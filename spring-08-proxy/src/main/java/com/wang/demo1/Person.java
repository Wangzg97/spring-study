package com.wang.demo1;

// 租房者
public class Person {
    public static void main(String[] args) {
        // 房东准备出租房子，找到中介
        Host host = new Host();
        // 中介帮忙出租房子，期间还有其他公共业务如看房，签合同等
        Proxy proxy = new Proxy(host);
        // 个人找中介租房
        proxy.rent();
    }
}
