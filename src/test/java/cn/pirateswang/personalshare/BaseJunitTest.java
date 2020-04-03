package cn.pirateswang.personalshare;

import cn.pirateswang.PersonalshareApplication;
import cn.pirateswang.core.auth.controller.AuthLoginController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author liujy
 * @date 2019/11/6 14:56
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PersonalshareApplication.class)
public class BaseJunitTest {
    
    @Autowired
    private AuthLoginController controller;
    
    @Test
    public void testBase(){

        controller.logOut();
        
        
    }


}
