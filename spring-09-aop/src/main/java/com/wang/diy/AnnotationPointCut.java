package com.wang.diy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

// 方式三：使用注解实现AOP
@Aspect // 标注这是一个切面
public class AnnotationPointCut {
    @Before("execution(* com.wang.service.UserServiceImpl.*(..))")
    public void before(){
        System.out.println("=====before=====");
    }

    @After("execution(* com.wang.service.UserServiceImpl.*(..))")
    public void after(){
        System.out.println("=====after=====");
    }

    // 环绕增强中，可以获取切入点进行处理
    @Around("execution(* com.wang.service.UserServiceImpl.*(..))")
    public void aroud(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕前");

        Signature signature = joinPoint.getSignature();// 获得签名
        System.out.println("signature: " + signature);

        // 执行方法
        Object proceed = joinPoint.proceed();

        System.out.println("环绕后");
    }
}
