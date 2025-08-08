package net.sunday.effective.observation.agent.interceptor;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

/**
 * bytebuddy 方法拦截器
 */
public class InstMethodInterceptor {

    /*
     * @RuntimeType：指示 `Byte Buddy` 终止严格类型检查以支持运行时类型转换
     * @This: 需要增强类的对象
     * @SuperCall：原方法的调用
     */
    @RuntimeType
    public Object intercept(@This Object obj,
                            @AllArguments Object[] arguments,
                            @SuperCall Callable<?> zuper,
                            @Origin Method method) throws Throwable {

        final long startTime = System.nanoTime();

        Object res;
        try {
            res = zuper.call();
        } finally {
            System.out.println("Time elapsed: " + (System.nanoTime() - startTime) / 1000000 + " ms");
        }

        return res;
    }

}
