package com.wang.mapper;

import com.wang.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

// 获取SQLSession方式一：使用SqlSessionTemplate
public class UserMapperImpl implements UserMapper{

    private SqlSessionTemplate sessionTemplate;

    public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
        this.sessionTemplate = sessionTemplate;
    }

    public List<User> selectAllUser() {

        UserMapper mapper = sessionTemplate.getMapper(UserMapper.class);
        return mapper.selectAllUser();
    }
}
