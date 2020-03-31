package cn.pirateswang.personalshare.core.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userTest")
public class UserTest {

    @GetMapping("/sayHello")
    public String sayHello(){
        System.out.println("进入了一个测试方法");
        return "hello world 你好中国";
    }


}


