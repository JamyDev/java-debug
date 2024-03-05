package com.microsoft.java.debug.core.adapter.standalone;

import com.microsoft.java.debug.core.adapter.HotCodeReplaceEvent;
import com.microsoft.java.debug.core.adapter.IHotCodeReplaceProvider;
import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableFromIterable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class HotCodeReplaceProvider implements IHotCodeReplaceProvider {

    private Observable<HotCodeReplaceEvent> eventHub = new ObservableFromIterable<>(List.of());
    @Override
    public void onClassRedefined(Consumer<List<String>> consumer) {

    }

    @Override
    public CompletableFuture<List<String>> redefineClasses() {
        return null;
    }

    @Override
    public Observable<HotCodeReplaceEvent> getEventHub() {
        return eventHub;
    }
}
