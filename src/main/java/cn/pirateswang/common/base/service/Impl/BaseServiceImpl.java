package cn.pirateswang.common.base.service.Impl;

import cn.pirateswang.common.base.entity.BaseEntity;
import cn.pirateswang.common.base.mapper.BaseMapper;
import cn.pirateswang.common.base.service.BaseService;
import cn.pirateswang.common.publicVO.CurrentUser;
import cn.pirateswang.common.utils.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {
    
    @Autowired
    protected HttpServletRequest request;
    
    @Autowired
    protected HttpServletResponse response;
    
    @Autowired
    protected HttpSession session;
    
    @Autowired
    private BaseMapper<T> mapper;
    
    protected CurrentUser getCurrentUser(){
        CurrentUser currentUser = null;
        
        try {
            Object attribute = request.getSession().getAttribute(Repository.REQUEST_ATTRIBUTE.CURRENT_LOGIN_USER);
            if(attribute != null){
                currentUser = (CurrentUser) attribute;
            }
        }catch (Exception e){
            
        }
        return currentUser;
    }

    @Override
    public T get(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T getUnDeleted(Long id) {
        T t = mapper.selectByPrimaryKey(id);
        if(t != null && t.getDeleteFlg() != null && t.getDeleteFlg() == 0){
            return t;
        }
        return null;
    }

    @Override
    public int save(T t) {
        CurrentUser currentUser = getCurrentUser();
        if(currentUser == null){
            currentUser = new CurrentUser(new Long(0),"system");
        }
        Date currentDate = new Date();
        Long id = t.getId();
        if(id == null){
            t.setCreateTime(currentDate);
            t.setCreateUserId(currentUser.getId());
            t.setCreateUserName(currentUser.getUserName());
            t.setLastModifyTime(currentDate);
            t.setLastModifyUserId(currentUser.getId());
            t.setLastModifyUserName(currentUser.getUserName());
            t.setDeleteFlg(0);
            t.setVersion(1L);
            return mapper.insertSelective(t);
        }else{
            t.setLastModifyUserName(currentUser.getUserName());
            t.setLastModifyUserId(currentUser.getId());
            t.setLastModifyTime(currentDate);
            t.setVersion(t.getVersion() == null ? 1 : t.getVersion() + 1);
            return mapper.updateByPrimaryKey(t);
        }
    }

    @Override
    public int insertList(List<T> list) {
        if(list == null || list.isEmpty()){
            return 0;
        }
        CurrentUser currentUser = getCurrentUser();
        if(currentUser == null){
            currentUser = new CurrentUser(new Long(0),"system");
        }
        Date currentDate = new Date();
        T t = null;
        for (int cnt = 0;cnt < list.size();cnt ++){
            t = list.get(cnt);
            t.setCreateTime(currentDate);
            t.setCreateUserId(currentUser.getId());
            t.setCreateUserName(currentUser.getUserName());
            t.setLastModifyTime(currentDate);
            t.setLastModifyUserId(currentUser.getId());
            t.setLastModifyUserName(currentUser.getUserName());
            t.setDeleteFlg(0);
            t.setVersion(1L);
        }
        return mapper.insertList(list);
    }

    @Override
    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }
}
