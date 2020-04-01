package cn.pirateswang.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpringUtil implements ApplicationContextAware {
    
    private ApplicationContext context = null;
    
    private static SpringUtil self ;
    
    @PostConstruct
    public void init(){
        self = this;        
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
    
    public static Object getBean(String name){
        return self.context.getBean(name);
    }
    
    public static <T> T getBean(Class<T> clazz){
        return self.context.getBean(clazz);
    }
}
