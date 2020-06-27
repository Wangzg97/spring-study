package com.wang.demo4;

import com.wang.demo2.UserService;
import com.wang.demo2.UserServiceImpl;

public class Client {
    public static void main(String[] args) {
        // 真实角色
        UserService userService = new UserServiceImpl();
        // 代理角色
        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler();
        // 设置代理对象
        proxyInvocationHandler.setTarget(userService);
        // 生成代理类
        UserService proxy = (UserService) proxyInvocationHandler.getProxy();

        proxy.add();
    }
}
