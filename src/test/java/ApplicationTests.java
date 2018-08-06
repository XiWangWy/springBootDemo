import com.bless.Application;
import com.bless.Entity.User;
import com.bless.Repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wangxi on 18/7/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
//@WebAppConfiguration
//@EnableCaching
public class ApplicationTests {


//    @Autowired
//    private CacheManager cacheManager;

    @Autowired
    private UserRepository userRepository;



    @Before
    public void before(){
        userRepository.save(new User("wx","12345"));
    }


    @Test
    public void test(){
        User u1 = userRepository.findByUserName("wx");
        System.out.println("第一次查询：" + u1.getPassWord());

        User u2 = userRepository.findByUserName("wx");
        System.out.println("第二次查询：" + u2.getPassWord());

        u2.setPassWord("54321");
        userRepository.save(u2);
        User u3 = userRepository.findByUserName("wx");

        System.out.println("第三次查询：" + u3.getPassWord());
    }
}
