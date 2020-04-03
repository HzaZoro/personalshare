package cn.pirateswang.common.system;

import cn.pirateswang.common.config.ServiceConfig;
import cn.pirateswang.common.publicEnum.SystemInterfaceInstance;
import cn.pirateswang.common.tools.SystemBlackListTools;
import cn.pirateswang.common.utils.SpringUtil;
import cn.pirateswang.core.system.blackList.entity.SystemBlackListEntity;
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

        SystemBlackListTools systemBlackListTools = SystemBlackListTools.getInstance();

        List<String> whiteListDomainList = serviceConfig.getWhiteListDomainList();
//        List<String> blackVisitList = serviceConfig.getBlackVisitList();
        
        if(whiteListDomainList != null && !whiteListDomainList.isEmpty()){
            log.info("【系统启动】配置访问域名【白名单】");
            for (int cnt = 0;cnt <whiteListDomainList.size();cnt ++){
                log.info("---{}、 {}",cnt + 1,whiteListDomainList.get(cnt));
            }
        }else{
            log.info("【系统启动】未配置访问域名【白名单】");
        }

        final List<SystemBlackListEntity> blackVisitList = systemBlackListTools.getBlackList();
        if(blackVisitList != null && !blackVisitList.isEmpty()){
            log.info("【系统启动】共配置{}条系统访问【黑名单】",blackVisitList.size());
            for (int cnt = 0;cnt <blackVisitList.size();cnt ++){
                SystemBlackListEntity systemBlackListEntity = blackVisitList.get(cnt);
                String blackIp = systemBlackListEntity.getIp();
                Integer blackPort = systemBlackListEntity.getPort();
                Integer type = systemBlackListEntity.getType();
                if(type != null && type.intValue() == SystemInterfaceInstance.BLACK_TYPE_INSTANCE.IP_TYPE.intValue()){
                    log.info("【系统启动】===>【黑名单】 IP: {} Port:{} 拦截类型:{}",blackIp,blackPort,"IP拦截");

                }else if(type != null && type.intValue() == SystemInterfaceInstance.BLACK_TYPE_INSTANCE.PORT_TYPE.intValue()){
                    log.info("【系统启动】===>【黑名单】 IP: {} Port:{} 拦截类型:{}",blackIp,blackPort,"端口拦截");
                }else{
                    log.info("【系统启动】===>【黑名单】 IP: {} Port:{} 未发现的拦截类型",blackIp,blackPort,type);
                }

            }
        }else{
            log.info("【系统启动】未配置系统访问【黑名单】");
        }
        

        log.info("************【系统启动结束】************");
    }
}
