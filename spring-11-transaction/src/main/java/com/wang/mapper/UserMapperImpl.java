package com.wang.mapper;

import com.wang.pojo.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;


public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper{

    public List<User> getAllUser() {
        // addUser();

        // deleteUser();

        return getSqlSession().getMapper(UserMapper.class).getAllUser();
    }

    public int addUser(User user) {
        return getSqlSession().getMapper(UserMapper.class).addUser(user);
    }

    public int deleteUser(int id) {
        return getSqlSession().getMapper(UserMapper.class).deleteUser(id);
    }
}
