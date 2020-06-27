package com.wang.demo2;

public class client {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        UserServiceProxy proxy = new UserServiceProxy();
        proxy.setUserService(userService);

        proxy.add();
        proxy.delete();
        proxy.update();
        proxy.query();
    }
}
