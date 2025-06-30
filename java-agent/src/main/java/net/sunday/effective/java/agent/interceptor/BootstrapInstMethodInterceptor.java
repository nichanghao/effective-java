package net.sunday.effective.java.agent.interceptor;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

/**
 * 拦截 BootstrapClassLoader（JDK内部的类） 的实例方法调用
 */

public class BootstrapInstMethodInterceptor {

    /**
     * 注意方法签名为 static，避免实例创建，字节码插入时可以直接写 INVOKESTATIC，保证调用路径简单且性能最佳
     */
    @RuntimeType
    public static Object intercept(@This Object obj,
                                   @AllArguments Object[] arguments,
                                   @SuperCall Callable<?> zuper,
                                   @Origin Method method) throws Throwable {

        final long startTime = System.nanoTime();

        Object res;
        try {
            res = zuper.call();
        } finally {
            System.out.println("Class: " + obj.getClass().getName()
                                   + ", Time elapsed: " + (System.nanoTime() - startTime) / 1000000 + " ms");
        }

        return res;
    }
}
