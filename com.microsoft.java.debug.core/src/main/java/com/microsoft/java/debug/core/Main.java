package com.microsoft.java.debug.core;


import com.microsoft.java.debug.core.adapter.*;
import com.microsoft.java.debug.core.adapter.standalone.*;

public class Main {
    public static void main(String[] args) {
        ProtocolServer server = new ProtocolServer(System.in, System.out, getProviderContext());
        server.run();
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