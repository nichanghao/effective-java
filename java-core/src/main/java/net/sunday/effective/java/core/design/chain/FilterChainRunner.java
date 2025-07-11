package net.sunday.effective.java.core.design.chain;

import net.sunday.effective.java.core.design.chain.filter.*;
import net.sunday.effective.java.core.design.chain.invoker.FilterChainNodeInvoker;
import net.sunday.effective.java.core.design.chain.invoker.Invoker;

import java.util.List;

/**
 * 过滤器链
 */

public class FilterChainRunner {

    public static void main(String[] args) {

        Invoker<String, String> invoker = String::toUpperCase;
        Invoker<String, String> wrapperInvoker = initChainOfInvokers(invoker);

        System.out.println(wrapperInvoker.invoke("hello"));
    }


    private static <T, R> Invoker<T, R> initChainOfInvokers(Invoker<T, R> originInvoker) {
        Invoker<T, R> last = originInvoker;


        List<Filter> filters = List.of(new LoggerFilter(), new Logger2Filter());
        for (Filter filter : filters) {
            Invoker<T, R> next = last;
            last = new FilterChainNodeInvoker<>(filter, next);
        }

        return last;
    }
}
