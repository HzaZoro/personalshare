package cn.pirateswang.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "token")
@Component
public class TokenConfig {
    
    private String cookieName;
    
    private Exclude exclude = new Exclude();
    
    private String cookieSecrete;
    
    private Integer effectiveTime;

    public Integer getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Integer effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    
    public Exclude getExclude() {
        return exclude;
    }

    public void setExclude(Exclude exclude) {
        this.exclude = exclude;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getCookieSecrete() {
        return cookieSecrete;
    }

    public void setCookieSecrete(String cookieSecrete) {
        this.cookieSecrete = cookieSecrete;
    }

    public class Exclude{
        private List<String> urls;

        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }
    }
}
