package cn.pirateswang.core.user.service;

import cn.pirateswang.core.user.entity.UserInfoEntity;

import java.util.List;

public interface UserInfoService {

    List<UserInfoEntity> findByLoginName(String loginName);

}
