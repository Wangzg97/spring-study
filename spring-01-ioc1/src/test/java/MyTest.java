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
