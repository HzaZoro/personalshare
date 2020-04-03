package cn.pirateswang.core.system.blackList.service;

import cn.pirateswang.common.base.service.BaseService;
import cn.pirateswang.core.system.blackList.entity.SystemBlackListEntity;

import java.util.List;

public interface SystemBlackListService extends BaseService<SystemBlackListEntity> {
    
    List<SystemBlackListEntity> findAllBlackList();
    
}
