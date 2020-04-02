package cn.pirateswang.common.base.mapper;

import cn.pirateswang.common.base.entity.BaseEntity;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;

public interface BaseMapper<T extends BaseEntity> extends Mapper<T>, SelectByIdsMapper<T>, MySqlMapper<T> {

}
