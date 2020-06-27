package com.wang.demo2;

public class UserServiceImpl implements UserService {
    public void add() {
        System.out.println("增加了一条记录");
    }

    public void delete() {
        System.out.println("删除了一条记录");
    }

    public void update() {
        System.out.println("更新了一条记录");
    }

    public void query() {
        System.out.println("查询了一条记录");
    }
}
