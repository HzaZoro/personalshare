package cn.pirateswang.core.user.mapper;

import cn.pirateswang.common.base.mapper.BaseMapper;
import cn.pirateswang.core.user.entity.UserInfoEntity;
import cn.pirateswang.core.user.mapper.sql.UserInfoProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoEntity> {

    @SelectProvider(type = UserInfoProvider.class,method = "findByLoginName")
    public List<UserInfoEntity> findByLoginName(@Param("loginName") String loginName);


}
