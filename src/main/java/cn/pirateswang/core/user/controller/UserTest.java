package cn.pirateswang.core.user.controller;

import cn.pirateswang.common.utils.SpringUtil;
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
        UserInfoService bean = SpringUtil.getBean(UserInfoService.class);

        List<UserInfoEntity> userInfoList = bean.findByLoginName(loginName);
        return JSON.toJSONString(userInfoList);
    }

//    @GetMapping("/getKey")
//    public String getKey() throws NoSuchAlgorithmException {
//        Map<String, Object> stringObjectMap = RSAUtil.genKeyPair();
//        String publicKey = RSAUtil.getPublicKey(stringObjectMap);
//        String privateKey = RSAUtil.getPrivateKey(stringObjectMap);
//        System.out.println("公钥: "+publicKey);
//        System.out.println("私钥: "+privateKey);
//        return publicKey + " ======== " + privateKey;
//    }

//    @GetMapping("/test")
//    public String test() throws Exception {
//        String data = "EncryPt";
//        String s = RSAUtil.encryptByPublicKey(data);
//        return s;
//    }


}


