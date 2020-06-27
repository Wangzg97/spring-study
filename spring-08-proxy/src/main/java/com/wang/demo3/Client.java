package com.wang.demo3;

public class Client {

    public static void main(String[] args) {
        // 真实角色
        Host host = new Host();

        // 代理角色，现在还未生成
        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler();
        // 设置要代理的对象
        proxyInvocationHandler.setRent(host);
        // 动态生成代理类
        Rent proxy = (Rent) proxyInvocationHandler.getProxy();
        proxy.rent();
    }

}
