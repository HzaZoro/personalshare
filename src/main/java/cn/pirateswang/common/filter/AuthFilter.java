package cn.pirateswang.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Order(2)
@WebFilter(filterName = "authFilter",urlPatterns = "/*")
@Component
public class AuthFilter implements Filter {

    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("============>AuthFilter-------->doFilter 【START】");
        
        
        
        
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("<============AuthFilter<--------doFilter 【E N D】");
    }
}
