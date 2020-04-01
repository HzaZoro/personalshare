package cn.pirateswang.core.auth.service.impl;

import cn.pirateswang.common.publicEnum.ErrorEnum;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.common.utils.ResultVOUtil;
import cn.pirateswang.core.auth.dto.AuthLoginDTO;
import cn.pirateswang.core.auth.service.AuthLoginService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthLoginServiceImpl implements AuthLoginService {
    
    Logger log = LoggerFactory.getLogger(getClass());


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
        
        

        log.info("<=====AuthLoginServiceImpl<-----login 【E N D】");
        return null;
    }
}
