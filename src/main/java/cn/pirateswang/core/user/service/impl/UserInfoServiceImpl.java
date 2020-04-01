package cn.pirateswang.core.user.service.impl;

import cn.pirateswang.core.user.entity.UserInfoEntity;
import cn.pirateswang.core.user.mapper.UserInfoMapper;
import cn.pirateswang.core.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper mapper;

    @Override
    public List<UserInfoEntity> findByLoginName(String loginName) {

        return mapper.findByLoginName(loginName);
    }
}
