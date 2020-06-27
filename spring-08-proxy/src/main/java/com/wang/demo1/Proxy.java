package com.wang.demo1;

// 代理，中介
public class Proxy implements Rent{

    private Host host;

    public Proxy() {
    }

    public Proxy(Host host) {
        this.host = host;
    }

    public void rent() {
        seeHouse();
        host.rent();
        contract();
    }

    public void seeHouse(){
        System.out.println("看房子");
    }
    public void contract(){
        System.out.println("签合同");
    }
}
