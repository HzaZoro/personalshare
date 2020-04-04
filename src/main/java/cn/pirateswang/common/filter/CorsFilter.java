package cn.pirateswang.common.filter;

import cn.pirateswang.common.config.TokenConfig;
import cn.pirateswang.common.utils.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Order(1)
//@WebFilter(filterName = "corsFilter",urlPatterns = "/*")
@Component
public class CorsFilter implements Filter {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private TokenConfig tokenConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(tokenConfig == null){
            tokenConfig = SpringUtil.getBean(TokenConfig.class);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        log.info("【跨域设置】====>【START】");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Origin", StringUtils.isBlank(request.getHeader("origin")) ? "*" : request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Methods","POST,GET,DELETE,PUT,OPTIONS,HEAD");
        response.setHeader("Access-Control-Max-Age","7200");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,Authorization,Content-Type,sign,source,"+tokenConfig.getCookieName());
        response.setHeader("Access-Control-Allow-Credentials","true");
        log.info("【跨域设置】<====【E N D】");
        chain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
