package net.sunday.effective.design.pattern.proxy.service;


/**
 * 用户服务实现类
 */

public class UserServiceImpl implements UserService {
    @Override
    public void login() {
        System.out.println("logining...");
    }
}
