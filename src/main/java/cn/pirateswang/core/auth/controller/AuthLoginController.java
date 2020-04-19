package cn.pirateswang.core.auth.controller;

import cn.pirateswang.common.publicVO.CurrentUser;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.core.auth.dto.AuthLoginDTO;
import cn.pirateswang.core.auth.service.AuthLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(value = "/auth")
@Api(tags = {"用户登录/注销"})
public class AuthLoginController {
    
    @Autowired
    private AuthLoginService authLoginService;

    @ApiOperation(value = "登陆")
    @PostMapping("/login")
    public ResultVO<CurrentUser> login(@RequestBody AuthLoginDTO loginDTO){
        return authLoginService.login(loginDTO);
    }

    @ApiOperation(value = "注销")
    @GetMapping("/logOut")
    public void logOut(){
        authLoginService.logOut();
    }
    


}
