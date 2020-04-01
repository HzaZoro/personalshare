package cn.pirateswang.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "service")
@Component
public class ServiceConfig {
    
    private List<String> whiteListDomainList;
    
    private List<String> blackVisitList;

    public List<String> getBlackVisitList() {
        return blackVisitList;
    }

    public void setBlackVisitList(List<String> blackVisitList) {
        this.blackVisitList = blackVisitList;
    }

    public List<String> getWhiteListDomainList() {
        return whiteListDomainList;
    }

    public void setWhiteListDomainList(List<String> whiteListDomainList) {
        this.whiteListDomainList = whiteListDomainList;
    }
}
