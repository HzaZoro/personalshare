package cn.pirateswang.common.utils;

import cn.pirateswang.common.config.TokenConfig;
import cn.pirateswang.common.publicVO.CurrentUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class CookieUtil {
    
    private static Logger log = LoggerFactory.getLogger(CookieUtil.class);
    
    @Autowired
    private TokenConfig tokenConfig;
    
    private static CookieUtil self;
    
    @PostConstruct
    public void init(){
        self = this;
    }
    
    public static String setCookie(HttpServletResponse response, CurrentUser currentUser){
        String token = TokenUtil.getToken(currentUser);
        String cookieName = self.tokenConfig.getCookieName();
        Integer effectiveTime = self.tokenConfig.getEffectiveTime();
        set(response,cookieName,token,effectiveTime);
        return token;
    }
    
    public static void clearCookie(HttpServletResponse response){
        String cookieName = self.tokenConfig.getCookieName();
        set(response,cookieName,"clearCookie",0);
    }
    
    public static Cookie getCookie(HttpServletRequest request,String cookieName){
        Map<String, Cookie> cookieMap = readCookie(request);
        if(cookieMap.containsKey(cookieName)){
            return cookieMap.get(cookieName);
        }else{
            return null;            
        }
    }
    
    private static void set(HttpServletResponse response,String cookieName,String token,Integer effectiveTime){
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setMaxAge(effectiveTime);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    private static Map<String,Cookie> readCookie(HttpServletRequest request){
        log.info("====>readCookie 【START】");

        String cookieName = self.tokenConfig.getCookieName();
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        boolean hasCookie = false;
        if(cookies != null){
            for (Cookie cookie : cookies){
                cookieMap.put(cookie.getName(),cookie);
                if(StringUtils.equals(cookieName,cookie.getName())){
                    hasCookie = true;
                }
            }
        }
        
        if(hasCookie){
            log.info("请求对象中带有系统所需Cookie");
            return cookieMap;
        }else{
            log.info("未从Cookie中发现系统所需cookie,从Header中尝试获取token信息");
        }

        String headerCookie = request.getHeader(cookieName);
        if(StringUtils.isNotBlank(headerCookie)){
            log.info("从【请求头】中发现系统所需token:{},重置该token至Cookie中");
            Cookie cookie = new Cookie(cookieName, headerCookie);
            cookie.setMaxAge(self.tokenConfig.getEffectiveTime());
            cookie.setPath("/");
            cookieMap.put(cookieName,cookie);
            return cookieMap;
        }else{
            log.info("从【请求头】中未发现系统所需token:{} ,尝试从请求头中获取名字为cookie的信息",cookieName);
        }

        String cookieInHeader = request.getHeader("cookie");
        if(StringUtils.isBlank(cookieInHeader)){
            return cookieMap;
        }

        Cookie cookie = new Cookie(cookieName, cookieInHeader);
        cookie.setPath("/");
        cookie.setMaxAge(self.tokenConfig.getEffectiveTime());
        cookieMap.put(cookieName,cookie);
        log.info("<==== readCookie 【E N D】");
        return cookieMap;
    }
    
}
