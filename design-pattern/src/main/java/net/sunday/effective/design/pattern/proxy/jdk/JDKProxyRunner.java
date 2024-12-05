package net.sunday.effective.design.pattern.proxy.jdk;

import net.sunday.effective.design.pattern.proxy.jdk.invocation.UserServiceInvocationHandler;
import net.sunday.effective.design.pattern.proxy.service.UserService;
import net.sunday.effective.design.pattern.proxy.service.UserServiceImpl;

import java.lang.reflect.Proxy;

/**
 * jdk 动态代理 运行类
 */
public class JDKProxyRunner {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        // 主要装载器、一组接口及调用处理动态代理实例
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(), new UserServiceInvocationHandler(userService));
        userServiceProxy.login();

    }

}
