package com.wang.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 此处的@Component相当于在xml中声明<bean id="user" class="com.wang.pojo.User"/>。默认就是user，即类名小写
@Component
public class User {

    // 直接赋值
//    public String name = "zhangsan";
    // 显式赋值
    //相当于<bean id="user" class="com.wang.pojo.User">
    //        <property name="name" value="张三"/>
    //     </bean>
    @Value("lisi")
    public String name;
}
