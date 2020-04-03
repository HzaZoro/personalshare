package cn.pirateswang.core.system.blackList.mapper;

import cn.pirateswang.common.base.mapper.BaseMapper;
import cn.pirateswang.core.system.blackList.entity.SystemBlackListEntity;
import cn.pirateswang.core.system.blackList.mapper.sql.SystemBlackListProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SystemBlackListMapper extends BaseMapper<SystemBlackListEntity> {
    
    @SelectProvider(type = SystemBlackListProvider.class,method = "findAllBlackList")
    public List<SystemBlackListEntity> findAllBlackList();
    
    
}
