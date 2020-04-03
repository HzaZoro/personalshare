package cn.pirateswang.common.tools;

import cn.pirateswang.common.utils.SpringUtil;
import cn.pirateswang.core.system.blackList.entity.SystemBlackListEntity;
import cn.pirateswang.core.system.blackList.service.SystemBlackListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SystemBlackListTools {

    private static SystemBlackListTools tools = null;
    
    private Logger log = LoggerFactory.getLogger(SystemBlackListTools.class);
    
    private SystemBlackListService systemBlackListService;
    
    private List<SystemBlackListEntity> blackList = new ArrayList<>();
    
    public static SystemBlackListTools getInstance(){
        if(tools == null){
            tools = new SystemBlackListTools();
        }
        return tools;
    }
    
    public List<SystemBlackListEntity> getBlackList(){
        if(blackList == null || blackList.isEmpty()){
            init();
        }
        return blackList;
    }
    
    private void init(){
        log.info("【系统黑名单】初始化开始");
        systemBlackListService = SpringUtil.getBean(SystemBlackListService.class);
        synchronized (blackList){
            blackList = systemBlackListService.findAllBlackList();
            if(blackList == null || blackList.isEmpty()){
                log.info("【系统黑名单】未加载到相关黑名单列表信息");
            }else{
                log.info("【系统黑名单】加载到{}条黑名单信息",blackList.size());
                for (int cnt = 0;cnt < blackList.size();cnt++){
                    log.info("【系统黑名单】 {}、IP: {} --- Port:{} ",cnt + 1,blackList.get(cnt).getIp(),blackList.get(cnt).getPort());
                }
            }
        }
    }
    
    
    
    
}
