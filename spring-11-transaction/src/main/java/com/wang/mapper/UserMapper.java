package com.wang.mapper;

import com.wang.pojo.User;

import java.util.List;

public interface UserMapper {
    public List<User> getAllUser();

    public int addUser(User user);

    public int deleteUser(int id);
}
