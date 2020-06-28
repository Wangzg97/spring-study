import com.wang.mapper.UserMapper;
import com.wang.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Test {

    // 单独测试Mybatis用
    @org.junit.Test
    public void test1() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream stream = Resources.getResourceAsStream(resource);
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(stream);
        SqlSession sqlSession = build.openSession(true);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.selectAllUser();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    // 用Mybatis-spring的测试--获取Session方式一
    @org.junit.Test
    public void test2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        for (User user : userMapper.selectAllUser()) {
            System.out.println(user);
        }
    }

    // 用Mybatis-spring的测试--获取Session方式二
    @org.junit.Test
    public void test3(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper2", UserMapper.class);
        for (User user : userMapper.selectAllUser()) {
            System.out.println(user);
        }
    }
}
