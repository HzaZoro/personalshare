package cn.pirateswang.core.user.controller;

import cn.pirateswang.core.user.entity.UserInfoEntity;
import cn.pirateswang.core.user.service.UserInfoService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userTest")
public class UserTest {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam String loginName){
        System.out.println("进入了一个测试方法");
        List<UserInfoEntity> userInfoList = userInfoService.findByLoginName(loginName);
        return JSON.toJSONString(userInfoList);
    }


}


