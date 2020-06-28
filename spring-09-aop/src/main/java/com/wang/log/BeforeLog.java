package com.wang.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

// 方式一:使用Spring的接口实现AOP
public class BeforeLog implements MethodBeforeAdvice {

    // method：要执行的目标对象的方法
    // args：参数
    // Object：目标对象
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("将要执行类"+target.getClass().getName()+"的方法"+method.getName());
    }
}
