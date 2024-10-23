package com.microsoft.java.debug.core;


import com.microsoft.java.debug.core.adapter.*;
import com.microsoft.java.debug.core.adapter.standalone.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            var serverSocket = new ServerSocket(2222);
            var clientSocket = serverSocket.accept();
            ProtocolServer server = new ProtocolServer(clientSocket.getInputStream(), clientSocket.getOutputStream(), getProviderContext());
            server.run();

            server.stop();
            clientSocket.close();
            serverSocket.close();
        } catch(IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static IProviderContext getProviderContext() {
        var ctx = new ProviderContext();

        ctx.registerProvider(IVirtualMachineManagerProvider.class, new VirtualMachineManagerProvider());
        ctx.registerProvider(ICompletionsProvider.class, new CompletionsProvider());
        ctx.registerProvider(IEvaluationProvider.class, new EvaluationProvider());
        ctx.registerProvider(IHotCodeReplaceProvider.class, new HotCodeReplaceProvider());
        ctx.registerProvider(ISourceLookUpProvider.class, new SourceLookupProvider());

        return ctx;
    }
}