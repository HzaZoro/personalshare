package cn.pirateswang.core.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "登录数据")
public class AuthLoginDTO {

    @ApiModelProperty(value = "用户名",required = true)
    private String loginName;

    @ApiModelProperty(value = "密码",required = true)
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
