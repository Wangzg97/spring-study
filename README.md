# spring-study
# Spring



## 简介

1. 理念：

   是现有的技术更加容易使用，整合了现有的技术框架。

2. 导入maven库

   ```xml
   <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-webmvc</artifactId>
       <version>5.2.0.RELEASE</version>
   </dependency>
   
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-jdbc</artifactId>
       <version>5.2.0.RELEASE</version>
   </dependency>
   
   ```

3. 优点
   - 是一个免费的开源框架
   - 轻量级、非入侵式的框架
     - 非入侵式：引入Spring后不会改变原来代码造成影响。
   - ==控制反转（IOC）、面向切面编程（AOP）==
   - 支持事务的处理，对框架整合的支持

4. 组成

![image-20200625162619930](C:\Users\wangzg\AppData\Roaming\Typora\typora-user-images\image-20200625162619930.png)

5. 扩展
   - Spring Boot
     - 一个快速开发的脚手架
     - 基于SpringBoot可快速开发单个微服务
     - ==约定大于配置==
   - Spring Cloud
     - 基于Spring Boot实现的
   - Spring弊端：发展太久违背了原来的理念，配置十分繁琐，“配置地狱”。



## IOC理论

#### IOC原型

```java
package com.wang.dao;

public interface UserDao {
    void getUser();
}
```

```java
package com.wang.dao;

public class UserDaoImpl implements UserDao{
    public void getUser() {
        System.out.println("默认获取用户的数据");
    }
}
```

```java
package com.wang.dao;

public class UserDaoMysqlImpl implements UserDao{
    public void getUser() {
        System.out.println("Mysql获取用户的数据");
    }
}
```

```java
package com.wang.dao;

public class UserDaoOracleImpl implements UserDao{
    public void getUser() {
        System.out.println("Oracle获取用户的数据");
    }
}
```

```java
package com.wang.service;

public interface UserService {
    void getUser();
}
```

```java
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
```

```java
import com.wang.dao.UserDaoOracleImpl;
import com.wang.service.UserService;
import com.wang.service.UserServiceImpl;

public class MyTest {
    public static void main(String[] args) {

        // 原方式
//        UserService userService = new UserServiceImpl();
//        userService.getUser();

        UserService userService2 = new UserServiceImpl();
        ((UserServiceImpl)userService2).setUserDao(new UserDaoOracleImpl());
        userService2.getUser();
    }
}
```

原始的方法中，对象是由程序编写者主动创建的。

使用set方法进行注入，程序是被动的接收对象。进而可以专注于业务上的实现。



#### IOC介绍

> IoC is also known as dependency injection (DI). It is a process whereby objects define their dependencies (that is, the other objects they work with) only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean. This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies by using direct construction of classes or a mechanism such as the Service Locator pattern.

**控制反转是一种通过描述（xml或注解）并通过第三方去产生或获取特定的对象的方式。在Spring中实现控制反转的是IOC容器，其实现方法是依赖注入（Dependency Injection，DI）**

对象由Spring来创建、管理、装配



## Hello Spring

```java
package com.wang.pojo;

public class Hello {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "com.wang.pojo.Hello{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

beans.xml--名字无具体要求

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 使用Spring来创建对象，在Spring中称为bean

     原始：Hello hello = new Hello();
     在这里：id相当于变量名hello，class相当于类型Hello
     property相当于给对象属性赋值；

     -->
    <bean id="hello" class="com.wang.pojo.Hello">
        <property name="name" value="Spring"/>
    </bean>

</beans>
```

```java
import com.wang.pojo.Hello;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        // 获取Spring的上下文对象
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // 对象已经被Spring管理，直接从中取出即可使用
        Hello hello = (Hello)context.getBean("hello");
        System.out.println(hello.toString());
    }
}
```



## IOC创建对象的方式

