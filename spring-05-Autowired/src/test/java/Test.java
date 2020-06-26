import com.wang.pojo.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    @org.junit.Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Person person = (Person) context.getBean("person");
        person.getCat().Shout();
        person.getDog().Shout();
    }

    @org.junit.Test
    public void test2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans2.xml");
        Person person = (Person) context.getBean("person");
        person.getCat().Shout();
        person.getDog().Shout();
    }
}
