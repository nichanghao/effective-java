package net.sunday.effective.java.core.design.chain.filter;


import net.sunday.effective.java.core.design.chain.invoker.Invoker;

public interface Filter {

    <T, R> R filter(Invoker<T, R> invoker, T t);

}
