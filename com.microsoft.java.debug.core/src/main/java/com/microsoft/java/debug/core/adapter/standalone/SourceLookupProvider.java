package com.microsoft.java.debug.core.adapter.standalone;

import com.microsoft.java.debug.core.DebugException;
import com.microsoft.java.debug.core.JavaBreakpointLocation;
import com.microsoft.java.debug.core.adapter.ISourceLookUpProvider;
import com.microsoft.java.debug.core.protocol.Types;

import java.util.List;

public class SourceLookupProvider implements ISourceLookUpProvider {
    @Override
    public boolean supportsRealtimeBreakpointVerification() {
        return false;
    }

    @Override
    public String[] getFullyQualifiedName(String uri, int[] lines, int[] columns) throws DebugException {
        return new String[0];
    }

    // /home/user/.../path/inside/monorepo/package/file.java:63
    // package name, class name, location
    @Override
    public JavaBreakpointLocation[] getBreakpointLocations(String sourceUri, Types.SourceBreakpoint[] sourceBreakpoints) throws DebugException {
        return new JavaBreakpointLocation[0];
    }

    @Override
    public String getSourceFileURI(String fullyQualifiedName, String sourcePath) {
        return null;
    }

    @Override
    public String getSourceContents(String uri) {
        return null;
    }

    @Override
    public List<MethodInvocation> findMethodInvocations(String uri, int line) {
        return null;
    }
}
