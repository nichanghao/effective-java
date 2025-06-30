package net.bytebuddy.listener;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.bytebuddy.agent.builder.AgentBuilder;

public class RedefinitionListener implements AgentBuilder.RedefinitionStrategy.Listener {

    @Override
    public void onBatch(int index, List<Class<?>> batch, List<Class<?>> types) {
        /* do nothing */
    }

    @Override
    public Iterable<? extends List<Class<?>>> onError(int index,
                                                      List<Class<?>> batch,
                                                      Throwable throwable,
                                                      List<Class<?>> types) {
        System.err.println("throwable: " + throwable + ", index: " + index + ", batch: " + batch + ", types: " + types);
        return Collections.emptyList();
    }

    @Override
    public void onComplete(int amount, List<Class<?>> types, Map<List<Class<?>>, Throwable> failures) {
        /* do nothing */
    }
}