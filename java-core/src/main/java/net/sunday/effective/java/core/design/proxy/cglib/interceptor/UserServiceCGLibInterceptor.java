package net.sunday.effective.java.core.design.proxy.cglib.interceptor;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib 动态代理
 */

public class UserServiceCGLibInterceptor implements MethodInterceptor {


    @SuppressWarnings("unchecked")
    public <T> T newInstance(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    /**
     * obj: 表示增强的对象，即实现MethodInterceptor这个接口类的一个对象；
     * method: 表示要被拦截的方法；
     * args: 表示要被拦截方法的参数；
     * proxy: 表示要触发父类的方法对象
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        System.out.println("dynamic cglib run start...");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("dynamic cglib run end...");

        return result;
    }
}
