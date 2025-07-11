package net.sunday.effective.java.core.design.chain.filter;


import net.sunday.effective.java.core.design.chain.invoker.Invoker;

public class Logger2Filter implements Filter {

    @Override
    public <T, R> R filter(Invoker<T, R> invoker, T t) {

        System.out.println("Logger2Filter req: " + t);

        return invoker.invoke(t);
    }
}
