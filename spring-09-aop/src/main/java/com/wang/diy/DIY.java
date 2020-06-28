package com.wang.diy;

// 方式二：自定义类实现AOP
public class DIY {

    public void before(){
        System.out.println("======before=====");
    }

    public void after(){
        System.out.println("======after=====");
    }
}
