package cn.pirateswang.core.auth.service;

import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.core.auth.dto.AuthLoginDTO;

public interface AuthLoginService {
    
    public ResultVO<?> login(AuthLoginDTO loginDTO);
    
    
}
