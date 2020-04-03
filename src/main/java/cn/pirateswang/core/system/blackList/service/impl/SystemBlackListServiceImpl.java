package cn.pirateswang.core.system.blackList.service.impl;

import cn.pirateswang.common.base.service.impl.BaseServiceImpl;
import cn.pirateswang.core.system.blackList.entity.SystemBlackListEntity;
import cn.pirateswang.core.system.blackList.mapper.SystemBlackListMapper;
import cn.pirateswang.core.system.blackList.service.SystemBlackListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemBlackListServiceImpl extends BaseServiceImpl<SystemBlackListEntity> implements SystemBlackListService {
    
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SystemBlackListMapper systemBlackListMapper;

    @Override
    public List<SystemBlackListEntity> findAllBlackList() {
        return systemBlackListMapper.findAllBlackList();
    }
    
}
