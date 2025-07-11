package net.sunday.effective.java.core.design.chain.invoker;


public interface Invoker<T, R> {

    R invoke(T t);
}
