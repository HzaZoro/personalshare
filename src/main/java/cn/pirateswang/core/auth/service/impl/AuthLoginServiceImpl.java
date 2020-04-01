package cn.pirateswang.core.auth.service.impl;

import cn.pirateswang.common.publicEnum.ErrorEnum;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.common.utils.RSAUtil;
import cn.pirateswang.common.utils.ResultVOUtil;
import cn.pirateswang.core.auth.dto.AuthLoginDTO;
import cn.pirateswang.core.auth.service.AuthLoginService;
import cn.pirateswang.core.user.entity.UserInfoEntity;
import cn.pirateswang.core.user.service.UserInfoService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthLoginServiceImpl implements AuthLoginService {
    
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public ResultVO<?> login(AuthLoginDTO loginDTO) {
        log.info("=====>AuthLoginServiceImpl----->login 【START】");
        log.info("REQUEST: {}", JSON.toJSONString(loginDTO));
        
        if(loginDTO == null){
            log.info("【登录失败】请求参数为空");
            return ResultVOUtil.error(ErrorEnum.REQUEST_PARAM_IS_NULL);
        }

        String loginName = loginDTO.getLoginName();
        String password = loginDTO.getPassword();
        if(StringUtils.isBlank(loginName)){
            log.info("【登录失败】用户名未填写");
            return ResultVOUtil.error(ErrorEnum.LOGIN_USER_NAME_IS_NULL);
        }
        if(StringUtils.isBlank(password)){
            log.info("【登录失败】密码未填写");
            return ResultVOUtil.error(ErrorEnum.LOGIN_PASSWORD_IS_NULL);
        }
        
        log.info("验证用户密码是否正确");
        List<UserInfoEntity> userList = userInfoService.findByLoginName(loginName);
        if(userList == null || userList.isEmpty()){
            log.info("【登录失败】通过该用户名未查询到用户信息,loginName:{}",loginName);
            return ResultVOUtil.error(ErrorEnum.USER_INFO_IS_NOT_EXIST);
        }

        if(userList.size() > 1){
            log.info("【登录失败】通过该用户名:{} 查询到{}条用户记录",loginName,userList.size());
            return ResultVOUtil.error(ErrorEnum.USER_INFO_MORE_THAN_ONE);
        }

        UserInfoEntity userInfo = userList.get(0);
        String localPassword = userInfo.getPassword();
        if(StringUtils.isBlank(localPassword)){
            log.info("【登录失败】系统未设置该用户的密码");
            return ResultVOUtil.error(ErrorEnum.SYSTEM_USER_INFO_ERROR);
        }

        try {
            //私钥解密前端传入密码
            String decryPassword = RSAUtil.decryptByPriKey(password);
            String decryLocalPassword = RSAUtil.decryptByPriKey(localPassword);
            if(!StringUtils.equals(decryPassword,decryLocalPassword)){
                log.info("【登录失败】密码有误");

            }else{
                log.info("【登录成功】");
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        log.info("<=====AuthLoginServiceImpl<-----login 【E N D】");
        return null;
    }
}
