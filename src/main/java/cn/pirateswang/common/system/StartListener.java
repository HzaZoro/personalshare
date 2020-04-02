package cn.pirateswang.common.system;

import cn.pirateswang.common.config.ServiceConfig;
import cn.pirateswang.common.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {
    
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 监听项目刷新启动事件
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("************【系统启动开始】************");
        ServiceConfig serviceConfig = SpringUtil.getBean(ServiceConfig.class);
        List<String> whiteListDomainList = serviceConfig.getWhiteListDomainList();
        List<String> blackVisitList = serviceConfig.getBlackVisitList();
        
        if(whiteListDomainList != null && !whiteListDomainList.isEmpty()){
            log.info("【系统启动】配置访问域名【白名单】");
            for (int cnt = 0;cnt <whiteListDomainList.size();cnt ++){
                log.info("---{}、 {}",cnt + 1,whiteListDomainList.get(cnt));
            }
        }else{
            log.info("【系统启动】未配置访问域名【白名单】");
        }
        
        if(blackVisitList != null && !blackVisitList.isEmpty()){
            log.info("【系统启动】配置访问IP【黑名单】");
            for (int cnt = 0;cnt <blackVisitList.size();cnt ++){
                log.info("---{}、 {}",cnt + 1,blackVisitList.get(cnt));
            }
        }else{
            log.info("【系统启动】未配置访问IP【黑名单】");
        }
        
        


        log.info("************【系统启动结束】************");
    }
}
