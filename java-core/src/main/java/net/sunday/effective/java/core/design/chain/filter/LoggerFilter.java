package net.sunday.effective.java.core.design.chain.filter;


import net.sunday.effective.java.core.design.chain.invoker.Invoker;

public class LoggerFilter implements Filter {

    @Override
    public <T, R> R filter(Invoker<T, R> invoker, T t) {

        System.out.println("LoggerFilter req: " + t);

        return invoker.invoke(t);
    }
}
