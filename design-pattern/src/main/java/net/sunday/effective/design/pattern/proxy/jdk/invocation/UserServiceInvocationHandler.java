package net.sunday.effective.design.pattern.proxy.jdk.invocation;

import net.sunday.effective.design.pattern.proxy.service.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 用户服务 invocation handler 实现
 */

public class UserServiceInvocationHandler implements InvocationHandler {

    private final UserService target;

    public UserServiceInvocationHandler(UserService userService) {
        this.target = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("dynamic jdk run start...");
        Object result = method.invoke(target, args);
        System.out.println("dynamic jdk run end...");

        return result;
    }
}
