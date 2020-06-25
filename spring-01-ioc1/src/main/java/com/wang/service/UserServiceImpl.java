package com.wang.service;

import com.wang.dao.UserDao;
import com.wang.dao.UserDaoImpl;
import com.wang.dao.UserDaoMysqlImpl;
import com.wang.dao.UserDaoOracleImpl;

public class UserServiceImpl implements UserService{

    // 每次添加新需求都要更改原有代码
//    private UserDao userDao = new UserDaoImpl();
//    private UserDao userDao = new UserDaoMysqlImpl();
//    private UserDao userDao = new UserDaoOracleImpl();

    //新方法--利用set进行动态实现值的注入
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void getUser() {
        userDao.getUser();
    }
}
