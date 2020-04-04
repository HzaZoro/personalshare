package cn.pirateswang.common.system;

import cn.pirateswang.common.filter.AuthFilter;
import cn.pirateswang.common.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemFilterConfiguration {

    @Autowired
    private AuthFilter authFilter;

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    public FilterRegistrationBean corsFilterBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(corsFilter);
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean authFilterBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(authFilter);
        bean.addUrlPatterns("/*");
        bean.setOrder(5);
        return bean;
    }

}
