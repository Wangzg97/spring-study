# Spring

*代码及笔记参考自https://space.bilibili.com/95256449*

Spring官方下载地址:https://repo.spring.io/release/org/springframework/spring/

Mybatis官方文档:https://mybatis.org/mybatis-3/zh/getting-started.html

Mybatis-spring官方文档:http://mybatis.org/spring/zh/getting-started.html



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

1. 使用**无参构造**创建对象（默认方式）

   ```java
   package com.wang.pojo;
   
   public class User {
       private String name;
   
       public String getName() {
           return name;
       }
   
       public void setName(String name) {
           this.name = name;
       }
   
       public void show(){
           System.out.println("name= "+name);
       }
   }
   ```

   beans.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd">
   
       <bean id="user" class="com.wang.pojo.User">
           <property name="name" value="zhangsan"/>
       </bean>
   
   </beans>
   ```

   ```java
   import com.wang.pojo.User;
   import org.springframework.context.support.ClassPathXmlApplicationContext;
   
   public class Test {
       public static void main(String[] args) {
           ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
           User user = (User) context.getBean("user");
           user.show();
       }
   }
   ```

   控制台输出为

   > D:\Environment\Java\jdk1.8.0_181\bin\java.exe "......
   > ==name= zhangsan==
   >
   > Process finished with exit code 0

2. 使用**有参构造**方式创建对象

   （1）下标赋值

   ```xml
   <bean id="user" class="com.wang.pojo.User">
       <constructor-arg index="0" value="里斯"/>
   </bean>
   ```

   （2）通过参数类型赋值

   ```xml
   <bean id="user" class="com.wang.pojo.User">
       <constructor-arg type="java.lang.String" value="王五"/>
   </bean>
   ```

   （3）直接通过参数名设置

   ```xml
   <bean id="user" class="com.wang.pojo.User">
       <constructor-arg name="name" value="王五"/>
   </bean>
   ```

   

## Spring配置

1. 别名

```xml
<alias name="user" alias="user2"/>
```

或者是用name，可以同时取多个别名

```xml
<bean id="user" class="com.wang.pojo.User" name="user2, user3"/>
```

2. import

   一般用于团队开发使用，可以将多个配置文件导入合并为一个。

   ```xml
   <import resource="beans1.xml"/>
   <import resource="beans2.xml"/>
   <import resource="beans3.xml"/>
   ```

   

## DI依赖注入

1. 构造器注入

2. ==**通过set方式注入**==

   - 依赖注入：

     - 依赖：bean对象的创建依赖于容器
     - 注入：bean对象中的所有属性由容器来注入

   - 实例代码：

     beans.xml

     ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.springframework.org/schema/beans
             https://www.springframework.org/schema/beans/spring-beans.xsd">
     
         <bean id="student" class="com.wang.pojo.Student">
             <!-- 普通值注入，value -->
             <property name="name" value="张三"/>
             <!-- bean注入，ref -->
             <property name="address" ref="address"/>
             <!-- 数组注入 -->
             <property name="books">
                 <array>
                     <value>语文</value>
                     <value>数学</value>
                     <value>英语</value>
                 </array>
             </property>
             <!-- List注入 -->
             <property name="hobbys">
                 <list>
                     <value>跳舞</value>
                     <value>读书</value>
                     <value>听音乐</value>
                 </list>
             </property>
             <!-- Map注入 -->
             <property name="card">
                 <map>
                     <entry key="校园卡" value="12345"/>
                     <entry key="借书卡" value="56789"/>
                 </map>
             </property>
             <!-- Set注入 -->
             <property name="games">
                 <set>
                     <value>英雄联盟</value>
                     <value>守望先锋</value>
                 </set>
             </property>
             <!-- Null注入 -->
     <!--        <property name="wife" value=""/>-->
             <property name="wife">
                 <null/>
             </property>
             <!-- Properties注入 -->
             <property name="info">
                 <props>
                     <prop key="性别">男</prop>
                     <prop key="年龄">23</prop>
                 </props>
             </property>
         </bean>
     
         <bean id="address" class="com.wang.pojo.Address">
             <!-- 普通值注入，value -->
             <property name="address" value="明德路1号"/>
         </bean>
     
     </beans>
     ```

     

3. 其他方式

   - p-namespace
   - c-namespace

4. bean作用域

   ##### singleton

   - 默认机制。scope="singleton"
   - IOC容器仅创建一个Bean实例，IOC容器每次返回的是同一个Bean实例。

   ##### prototype

   - IOC容器可以创建多个Bean实例，每次返回的都是一个新的实例。

   ##### request

   - 该属性仅对HTTP请求产生作用，使用该属性定义Bean时，每次HTTP请求都会创建一个新的Bean，适用于WebApplicationContext环境。

   ##### session

   - 该属性仅用于HTTP Session，同一个Session共享一个Bean实例。不同Session使用不同的实例。

   ##### global-session

   - 该属性仅用于HTTP Session，同session作用域不同时，所有的Session共享一个Bean实例。

   

## Bean的自动装配

- Spring会在上下文中自动寻找，并自动给bean装配属性。

- 三种装配方式：
  - 在xml中显式配置
  - 在Java中显式配置
  - ==**隐式的自动装配**==

### 测试

#### 环境搭建

```java
package com.wang.pojo;

public class Cat {

    public void Shout(){
        System.out.println("喵喵喵~");
    }
}
```

```java
package com.wang.pojo;

public class Dog {

    public void Shout(){
        System.out.println("汪汪汪~");
    }
}
```

```java
package com.wang.pojo;

public class Person {

    private String name;
    private Cat cat;
    private Dog dog;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", cat=" + cat +
                ", dog=" + dog +
                '}';
    }
}
```

#### byName自动装配

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="cat" class="com.wang.pojo.Cat"/>
    <bean id="dog" class="com.wang.pojo.Dog"/>

    <!--  byName:会自动在容器上下文中寻找，和自己对象set方法的参数值对应的bean-id 
 	要保证bean的id唯一  -->
    <bean id="person" class="com.wang.pojo.Person" autowire="byName">
        <property name="name" value="张三"/>
    </bean>

</beans>
```

#### byType自动装配

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="cat13413" class="com.wang.pojo.Cat"/>
    <bean id="dog425" class="com.wang.pojo.Dog"/>

    <!--  byName:会自动在容器上下文中寻找，和自己对象属性类型相同的bean 
 	要保证该类型的bean是唯一的  -->
    <bean id="person" class="com.wang.pojo.Person" autowire="byType">
        <property name="name" value="张三"/>
    </bean>

</beans>
```

#### 使用注解实现自 动装配

导入约束并配置注解的支持

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>

</beans>
```

1. **@Autowired**:

   直接在属性上使用，也可在set方法上使用。使用@Autowired可以不用编写set方法，前提是自动装配的属性在IOC容器中存在且名字符合规范。

   扩展：

   - 使用@nullable标记的字段可以为null。如

     ```java
     public void setName(@Nullable String name) {
         this.name = name;
     }
     ```

   - ```java
     @Autowired(required = false)  // 说明这个cat对象可以为null，否则不允许为空
     private Cat cat;
     ```

   - ```Java
     @Autowired
     @Qualifier(value = "dog2")
     private Dog dog;
     ```

     ```xml
     <bean id="dog" class="com.wang.pojo.Dog"/>
     <bean id="dog2" class="com.wang.pojo.Dog"/>
     ```

     可以使用@Qualifier(value = "xxx")来指定具体的bean对象注入。

2. **@Resource**

   ```Java
   public class Person {
   
       private String name;
       @Resource(name = "cat")
       private Cat cat;
       @Resource(name = "dog")
       private Dog dog;
       
   }
   ```

   - @Resource和@Autowired的联系和区别：
     - 都是用来自动装配的，都可以放在属性字段上；
     - @Autowired通过byType的方式实现；
     - @Resource默认通过byName来实现，如果找不到对应的名字则通过byType来实现；

3. **@Component**

   组件，一般放在类上方，表示类被Spring管理中

   - 衍生注解：按照MVC三层架构分层中。功能与@Component相同。

     （1）dao：**@Repository**

     （2）service：**@Service**

     （3）controller：**@Controller**



## 利用JavaConfig实现配置

```java
package com.wang.config;

import com.wang.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public User getUser(){
        return new User();
    }
}
```

```java
package com.wang.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User {

    private String name;

    public String getName() {
        return name;
    }
    @Value("张三")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

```java
import com.wang.config.AppConfig;
import com.wang.pojo.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

    @org.junit.Test
    public void test1(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        User user = (User) context.getBean("getUser");
        System.out.println(user.toString());
    }

}
```



## 代理模式

### 分类

- 静态代理
- 动态代理
- ![image-20200627152937505](C:\Users\wangzg\AppData\Roaming\Typora\typora-user-images\image-20200627152937505.png)

### 静态代理

#### 角色分析：

- 抽象角色：一般会使用接口或抽象类来解决
- 真实角色：被代理的角色
- 代理角色：代理真实角色，代理真实角色后一般会做一些附属操作
- 客户：访问代理对象的角色

#### 示例：

见spring-08-proxy文件夹内

#### 优缺点

- 优点：
  - 使真实角色的操作更加纯粹，不用关注一些公共业务
  - 公共业务交给代理角色，实现业务分工
  - 公共业务拓展时，方便集中管理
- 缺点：
  - 一个真实角色就会产生一个代理角色，代码量较大

### 动态代理

- 动态代理的代理类是动态生成的。

#### 分类

- 基于接口 -- JDK动态代理
- 基于类 -- cglib动态代理
- Java字节码实现 -- Javasist

#### 基础类：Proxy与InvocationHandler

- `Proxy`提供了创建动态代理类和实例的静态方法，它也是由这些方法创建的所有动态代理类的超类。

- `InvocationHandler`是由代理实例的*调用处理程序*实现的*接口* 。

  每个代理实例都有一个关联的调用处理程序。  当在代理实例上调用方法时，方法调用将被编码并分派到其调用处理程序的`invoke`方法。

#### 示例：

见spring-08-proxy文件夹内

#### 优点

- 使真实角色的操作更加纯粹，不用关注一些公共业务
- 公共业务交给代理角色，实现业务分工
- 公共业务拓展时，方便集中管理
- 一个动态代理类代理的是一个接口，一般就是对应的一类业务
- 一个动态代理类可以代理多个类

## AOP

###　概念



​	AOP(Aspect Oriented Programming):面向切面编程,通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术.利用AOP可以对因为业务逻辑的各个部分进行隔离,从而使得业务逻辑各部分之间的耦合性降低,提高程序的可重用性,同时提高开发效率.

### AOP在Spring中的作用

 ==提供声明式事务; 允许用户自定义切面==

![image-20200627183611968](C:\Users\wangzg\AppData\Roaming\Typora\typora-user-images\image-20200627183611968.png)

### 使用Spring实现AOP

导入依赖包

```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.13</version>
</dependency>
```

#### 方式一:使用Spring的接口

applicationContext.xml

```xml
    <bean id="beforeLog" class="com.wang.log.BeforeLog"/>
    <bean id="afterReturnLog" class="com.wang.log.AfterReturnLog"/>    
	<!-- 方式一：使用原生Spring API接口-->
    <aop:config>
        <!-- 配置切入点, execution(要执行的位置)-->
        <aop:pointcut id="pointcut" expression="execution(* com.wang.service.UserServiceImpl.*(..))"/>
        <!-- 执行环绕增加 -->
        <aop:advisor advice-ref="beforeLog" pointcut-ref="pointcut"/>
        <aop:advisor advice-ref="afterReturnLog" pointcut-ref="pointcut"/>
    </aop:config>
```

#### 方式二：自定义类

DIY.java

```java
package com.wang.diy;

public class DIY {

    public void before(){
        System.out.println("======before=====");
    }

    public void after(){
        System.out.println("======after=====");
    }
}
```

applicationContext.xml

```xml
<!-- 方式二：自定义类-->
    <bean id="diy" class="com.wang.diy.DIY"/>
    <aop:config>
        <!-- 自定义切面，ref要引用的类-->
        <aop:aspect ref="diy">
            <!-- 切入点 -->
            <aop:pointcut id="point" expression="execution(* com.wang.service.UserServiceImpl.*(..))"/>
            <!-- 通知 -->
            <aop:before method="before" pointcut-ref="point"/>
            <aop:after method="after" pointcut-ref="point"/>
        </aop:aspect>
    </aop:config>
```

#### 方式三：使用注解实现AOP

applicationContext.xml

```xml
	<!-- 方式三：使用注解-->
    <bean id="annotationPointCut" class="com.wang.diy.AnnotationPointCut"/>
    <!-- 开启注解支持   动态代理JDK(默认 proxy-target-class="false")  cglib(proxy-target-class="true") -->
    <aop:aspectj-autoproxy proxy-target-class="false"/>
```

AnnotationPointCut.java

```java
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

        Signature signature = joinPoint.getSignature();// 获得签名--方法的全限定名
        System.out.println("signature: " + signature);

        // 执行方法
        Object proceed = joinPoint.proceed();

        System.out.println("环绕后");
    }
}
```

## 整合Mybatis

步骤:

1. 导入jar包:
   - junit
   - mybatis
   - mysql
   - spring相关的
   - aop织入
   - mybatis-spring
2. 配置文件
3. 测试

#### Mybatis

1. 实体类
2. 核心配置文件
3. 编写接口
4. Mapper.xml
5. 测试

*(代码见spring-10-mybatis)*

#### Mybatis-spring

*(代码见spring-10-mybatis)*



## 声明式事务

事务:一组业务当作一个执行单元,要么都成功,要么都失败.

#### **ACID特性**

- 原子性（atomicity）。一个事务是一个不可分割的工作单位，事务中包括的操作要么都做，要么都不做。

- 一致性（consistency）。事务必须是使数据库从一个一致性状态变到另一个一致性状态。一致性与原子性是密切相关的。

- 隔离性（isolation）。一个事务的执行不能被其他事务干扰。即一个事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰。*防止数据损坏*

- 持久性（durability）。持久性也称永久性（permanence），指一个事务一旦提交，它对数据库中数据的改变就应该是永久性的。接下来的其他操作或故障不应该对其有任何影响。

