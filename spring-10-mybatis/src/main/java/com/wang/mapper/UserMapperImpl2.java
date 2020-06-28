package com.wang.mapper;

import com.wang.pojo.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

// 获取SQLSession方式二：SqlSessionDaoSupport
public class UserMapperImpl2 extends SqlSessionDaoSupport implements UserMapper {
    /*SqlSessionDaoSupport 是一个抽象的支持类，用来为你提供 SqlSession。
    调用 getSqlSession() 方法你会得到一个 SqlSessionTemplate，
    之后可以用于执行 SQL 方法*/
    public List<User> selectAllUser() {
        UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
        return mapper.selectAllUser();
    }
}
