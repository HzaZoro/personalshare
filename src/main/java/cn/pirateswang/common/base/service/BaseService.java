package cn.pirateswang.common.base.service;

import cn.pirateswang.common.base.entity.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    
    T get(Long id);
    
    T getUnDeleted(Long id);
    
    int save(T t);
    
    int insertList(List<T> list);
    
    List<T> findByIds(String ids);
    
    List<T> findAll();
    
}
