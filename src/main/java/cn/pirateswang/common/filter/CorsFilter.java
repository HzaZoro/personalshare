package cn.pirateswang.common.filter;

import cn.pirateswang.common.config.TokenConfig;
import cn.pirateswang.common.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "corsFilter",urlPatterns = "/*")
@Component
public class CorsFilter implements Filter {
    
    @Autowired
    private TokenConfig tokenConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(tokenConfig == null){
            tokenConfig = SpringUtils.getBean(TokenConfig.class);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods","POST,GET,DELETE,PUT,OPTIONS");
        response.setHeader("Access-Control-Max-Age","7200");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,Authorization,Content-Type,sign,source,"+tokenConfig.getTokenName());
        response.setHeader("Access-Control-Allow-Credentials","true");
        chain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
