package com.wang.log;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

// 方式一:使用Spring的接口实现AOP
public class AfterReturnLog implements AfterReturningAdvice {

    // returnValue:返回值
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println(method.getName()+"方法的返回值为："+returnValue);
    }
}
