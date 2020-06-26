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
