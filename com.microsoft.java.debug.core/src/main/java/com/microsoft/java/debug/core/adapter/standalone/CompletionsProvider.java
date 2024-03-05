package com.microsoft.java.debug.core.adapter.standalone;

import com.microsoft.java.debug.core.adapter.ICompletionsProvider;
import com.microsoft.java.debug.core.protocol.Types;
import com.sun.jdi.StackFrame;

import java.util.ArrayList;
import java.util.List;

public class CompletionsProvider implements ICompletionsProvider {
    @Override
    public List<Types.CompletionItem> codeComplete(StackFrame frame, String snippet, int line, int column) {
        return new ArrayList<>();
    }
}
