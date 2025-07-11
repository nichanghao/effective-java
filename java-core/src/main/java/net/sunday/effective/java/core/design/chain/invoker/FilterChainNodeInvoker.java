package net.sunday.effective.java.core.design.chain.invoker;

import lombok.AllArgsConstructor;
import net.sunday.effective.java.core.design.chain.filter.Filter;

@AllArgsConstructor
public class FilterChainNodeInvoker<T, R> implements Invoker<T, R> {

    private final Filter filter;

    private final Invoker<T, R> next;

    @Override
    public R invoke(T t) {
        return filter.filter(next, t);
    }
}
