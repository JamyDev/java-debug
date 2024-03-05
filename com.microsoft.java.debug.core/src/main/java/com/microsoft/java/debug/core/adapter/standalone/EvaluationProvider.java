package com.microsoft.java.debug.core.adapter.standalone;

import com.microsoft.java.debug.core.IEvaluatableBreakpoint;
import com.microsoft.java.debug.core.adapter.IEvaluationProvider;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;

import java.util.concurrent.CompletableFuture;

public class EvaluationProvider implements IEvaluationProvider {
    @Override
    public boolean isInEvaluation(ThreadReference thread) {
        return false;
    }

    @Override
    public CompletableFuture<Value> evaluate(String expression, ThreadReference thread, int depth) {
        return null;
    }

    @Override
    public CompletableFuture<Value> evaluate(String expression, ObjectReference thisContext, ThreadReference thread) {
        return null;
    }

    @Override
    public CompletableFuture<Value> evaluateForBreakpoint(IEvaluatableBreakpoint breakpoint, ThreadReference thread) {
        return null;
    }

    @Override
    public CompletableFuture<Value> invokeMethod(ObjectReference thisContext, String methodName, String methodSignature, Value[] args, ThreadReference thread, boolean invokeSuper) {
        return null;
    }

    @Override
    public void clearState(ThreadReference thread) {

    }
}
