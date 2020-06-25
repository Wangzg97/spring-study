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
