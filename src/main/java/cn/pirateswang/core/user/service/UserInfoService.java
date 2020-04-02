package cn.pirateswang.core.user.service;

import cn.pirateswang.common.base.service.BaseService;
import cn.pirateswang.core.user.entity.UserInfoEntity;

import java.util.List;

public interface UserInfoService extends BaseService<UserInfoEntity> {

    List<UserInfoEntity> findByLoginName(String loginName);

}
