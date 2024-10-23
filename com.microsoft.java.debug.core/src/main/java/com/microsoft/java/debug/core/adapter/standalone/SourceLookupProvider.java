package com.microsoft.java.debug.core.adapter.standalone;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.java.debug.core.DebugException;
import com.microsoft.java.debug.core.JavaBreakpointLocation;
import com.microsoft.java.debug.core.adapter.ISourceLookUpProvider;
import com.microsoft.java.debug.core.protocol.Types;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SourceLookupProvider implements ISourceLookUpProvider {
    @Override
    public boolean supportsRealtimeBreakpointVerification() {
        return true;
    }

    @Override
    public String[] getFullyQualifiedName(String uri, int[] lines, int[] columns) throws DebugException {
        return new String[0];
    }

    // sourceUri: file:///Users/jamy/Uber/fievel/dev-platform/frameworks/jfx/example/jfx-example-service-caller-v2/src/main/java/com/uber/jfx/example/caller/v2/JfxExampleServiceCallerV2.java
    // sourceBreakpoints: line: 10, col: -1, (condition, hitCondition, logMessage)
    //
    // javaBreakpointLocation: line: 10, col: -1, lineNumberInSourceFile: 10, className: com.uber.jfx.example.caller.v2.JfxExampleServiceCallerV2
    @Override
    public JavaBreakpointLocation[] getBreakpointLocations(String sourceUri, Types.SourceBreakpoint[] sourceBreakpoints) throws DebugException {
        try {
            URL url = new URL("http://127.0.0.1:27881/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Context-TTL-ms", "2000");
            conn.setRequestProperty("RPC-Caller", "java_debug");
            conn.setRequestProperty("RPC-Encoding", "json");
            conn.setRequestProperty("RPC-Procedure", "uber.devexp.ide.ulspdaemon.service.JDK::ResolveBreakpoints");
            conn.setRequestProperty("RPC-Service", "ulsp-daemon");
            conn.setDoOutput(true);
            JsonObject req = new JsonObject();
            req.addProperty("sourceUri", sourceUri);
            JsonArray breakpoints = new JsonArray();
            for (Types.SourceBreakpoint bp: sourceBreakpoints) {
                var jbp = new JsonObject();
                jbp.addProperty("line", bp.line);
                jbp.addProperty("column", bp.column);
                breakpoints.add(jbp);
            }
            req.add("breakpoints", breakpoints);
            var bytes = req.toString().getBytes("utf-8");
            conn.getOutputStream().write(bytes, 0, bytes.length);
            String response = getResponse(conn.getInputStream());
            if (response.isEmpty()) {
                return new JavaBreakpointLocation[]{};
            }

            Gson gson = new Gson();
            JsonObject res = gson.fromJson(response, JsonObject.class);
            List<JavaBreakpointLocation> jbpls = new ArrayList<>();
            for (var bp : res.getAsJsonArray("breakpointLocations")) {
                var bpo = bp.getAsJsonObject();
                var jbpl = new JavaBreakpointLocation(bpo.get("line").getAsInt(), bpo.get("column").getAsInt());
                jbpl.setLineNumberInSourceFile(jbpl.lineNumber());
                jbpl.setClassName(bpo.get("className").getAsString());
                jbpls.add(jbpl);
            }

            return jbpls.toArray(new JavaBreakpointLocation[0]);
        }
        catch (IOException e) {e.printStackTrace();}
        return new JavaBreakpointLocation[]{};
    }

    // fqn com.uber.jfx.example.caller.v2.JfxExampleServiceCallerV2
    // sp com/uber/jfx/example/caller/v2/JfxExampleServiceCallerV2.java

    // return /Users/jamy/Uber/fievel/dev-platform/frameworks/jfx/example/jfx-example-service-caller-v2/src/main/java/com/uber/jfx/example/caller/v2/JfxExampleServiceCallerV2.java
    @Override
    public String getSourceFileURI(String fullyQualifiedName, String sourcePath) {
        try {
            URL url = new URL("http://127.0.0.1:27881/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Context-TTL-ms", "2000");
            conn.setRequestProperty("RPC-Caller", "java_debug");
            conn.setRequestProperty("RPC-Encoding", "json");
            conn.setRequestProperty("RPC-Procedure", "uber.devexp.ide.ulspdaemon.service.JDK::ResolveClassToPath");
            conn.setRequestProperty("RPC-Service", "ulsp-daemon");
            conn.setDoOutput(true);
            JsonObject req = new JsonObject();
            req.addProperty("fullyQualifiedName", fullyQualifiedName);
            req.addProperty("sourceRelativePath", sourcePath);
            var bytes = req.toString().getBytes("utf-8");
            conn.getOutputStream().write(bytes, 0, bytes.length);
            String response = getResponse(conn.getInputStream());
            if (response.isEmpty()) {
                return "";
            }

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
            return jsonObject.get("sourceUri").getAsString();
        }
        catch (IOException e) {e.printStackTrace();}
        return "";
    }

    @Override
    public String getSourceContents(String uri) {
        return null;
    }

    @Override
    public List<MethodInvocation> findMethodInvocations(String uri, int line) {
        return null;
    }

    public String getResponse(InputStream stream) throws IOException {
        String buffer;
        StringBuffer response = new StringBuffer();
        BufferedReader res = new BufferedReader(new InputStreamReader(stream));
        while ((buffer = res.readLine()) != null)
            response.append(buffer);
        return response.toString();
    }
}
