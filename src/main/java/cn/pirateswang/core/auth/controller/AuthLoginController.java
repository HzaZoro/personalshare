package cn.pirateswang.core.auth.controller;

import cn.pirateswang.common.publicVO.CurrentUser;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.core.auth.dto.AuthLoginDTO;
import cn.pirateswang.core.auth.service.AuthLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthLoginController {
    
    @Autowired
    private AuthLoginService authLoginService;

    @PostMapping("/login")
    public ResultVO<CurrentUser> login(@RequestBody AuthLoginDTO loginDTO){
        return authLoginService.login(loginDTO);
    }
    
    @GetMapping("/logOut")
    public void logOut(){
        authLoginService.logOut();
    }
    


}
