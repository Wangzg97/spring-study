package com.wang.pojo;

public class Hello {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "com.wang.pojo.Hello{" +
                "name='" + name + '\'' +
                '}';
    }
}
