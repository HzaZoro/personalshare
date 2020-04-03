package cn.pirateswang.common.filter;

import cn.pirateswang.common.config.ServiceConfig;
import cn.pirateswang.common.config.TokenConfig;
import cn.pirateswang.common.publicEnum.ErrorEnum;
import cn.pirateswang.common.publicEnum.SystemInterfaceInstance;
import cn.pirateswang.common.publicVO.CurrentUser;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.common.tools.SystemBlackListTools;
import cn.pirateswang.common.utils.*;
import cn.pirateswang.core.system.accessRecord.entity.SystemAccessRecordEntity;
import cn.pirateswang.core.system.accessRecord.service.SystemAccessRecordService;
import cn.pirateswang.core.system.blackList.entity.SystemBlackListEntity;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Order(2)
@WebFilter(filterName = "authFilter",urlPatterns = "/*")
@Component
public class AuthFilter implements Filter {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private TokenConfig tokenConfig;
    
    @Autowired
    private ServiceConfig serviceConfig;
    
    @Autowired
    private SystemAccessRecordService systemAccessRecordService;
    
    private SystemBlackListTools systemBlackListTools = SystemBlackListTools.getInstance();

    private static Set<String> cacheUrlBuffer = new HashSet<>();
    
    private final String CONTENT_TYPE = "text/json;charset=utf-8";
    private final String OPTIONS_METHOD = "OPTIONS";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(tokenConfig == null){
            tokenConfig = SpringUtil.getBean(TokenConfig.class);
        }
        if(serviceConfig == null){
            serviceConfig = SpringUtil.getBean(ServiceConfig.class);
        }
        if(systemAccessRecordService == null){
            systemAccessRecordService = SpringUtil.getBean(SystemAccessRecordService.class);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("============>AuthFilter-------->doFilter 【START】");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Date currentDate = new Date();
        response.setContentType(CONTENT_TYPE);

        //请求方法
        String requestMethod = request.getMethod();
        //请求路径
        String servletPath = request.getServletPath();

        log.info("【SOURCE】: {} ",request.getHeader("source"));
        log.info("【REFERER】: {}",request.getHeader("Referer"));
        log.info("【SESSIONID】: {}",request.getSession().getId());
        log.info("【REQUEST-PATH】: {} | {}",requestMethod,servletPath);
        
        String ip = StringUtils.isBlank(request.getHeader("x-forwarded-for")) ? request.getRemoteAddr():request.getHeader("x-forwarded-for");
        log.info("【访问IP】: {}",ip);
        Integer serverPort = request.getServerPort();
        log.info("【访问端口】: {}",serverPort);

        if(StringUtils.isBlank(ip)){
            log.info("访问IP为空,验证不通过");
            response.getWriter().write(JSON.toJSONString(ResultVOUtil.error(ErrorEnum.ILLEGAL_IP_VISIT)));
            response.getWriter().flush();
            return;
        }

        SystemAccessRecordEntity systemAccessRecordEntity = new SystemAccessRecordEntity();
        //访问IP
        systemAccessRecordEntity.setIp(ip);
        //访问端口
        systemAccessRecordEntity.setPort(serverPort);
        //访问开始时间
        systemAccessRecordEntity.setRequestStartTime(currentDate);
        //访问路径
        systemAccessRecordEntity.setRequestPath(servletPath);
        
        List<SystemBlackListEntity> blackVisitList = systemBlackListTools.getBlackList();

        if(systemAccessRecordService == null){
            systemAccessRecordService = SpringUtil.getBean(SystemAccessRecordService.class);
        }
        
        if(blackVisitList != null && !blackVisitList.isEmpty()){
            log.info("【黑名单拦截】系统有设置访问黑名单");
            for (int cnt = 0;cnt <blackVisitList.size();cnt ++){
                SystemBlackListEntity systemBlackListEntity = blackVisitList.get(cnt);
                String blackIp = systemBlackListEntity.getIp();
                Integer blackPort = systemBlackListEntity.getPort();
                Integer type = systemBlackListEntity.getType();
                if(type != null && type.intValue() == SystemInterfaceInstance.BLACK_TYPE_INSTANCE.IP_TYPE.intValue()){
                    if(StringUtils.equals(ip,blackIp)){
                        log.info("【黑名单拦截】IP: {} Port:{} 拦截类型:{}",blackIp,blackPort,"IP拦截");
                        log.info("【黑名单拦截】该访问IP被禁止,IP:{}",ip);
                        this.insertSystemRecord(systemAccessRecordEntity,ErrorEnum.ILLEGAL_IP_VISIT);
                        response.getWriter().write(JSON.toJSONString(ResultVOUtil.error(ErrorEnum.ILLEGAL_IP_VISIT)));
                        response.getWriter().flush();
                        return;
                    }
                }else if(type != null && type.intValue() == SystemInterfaceInstance.BLACK_TYPE_INSTANCE.PORT_TYPE.intValue()){
                    if(serverPort != null && blackPort != null && blackPort.intValue() == serverPort.intValue()){
                        log.info("【黑名单拦截】===> IP: {} Port:{} 拦截类型:{}",blackIp,blackPort,"端口拦截");
                        log.info("【黑名单拦截】禁止访问该端口:{},IP:{}",serverPort,ip);
                        this.insertSystemRecord(systemAccessRecordEntity,ErrorEnum.ILLEGAL_PORT_VISIT);
                        response.getWriter().write(JSON.toJSONString(ResultVOUtil.error(ErrorEnum.ILLEGAL_PORT_VISIT)));
                        response.getWriter().flush();
                        return;
                    }
                }
            }
            log.info("【黑名单拦截】该访问被允许");
        }else{
            log.info("【黑名单拦截】系统未配置访问黑名单");
        }
        
        //系统白名单设置
        List<String> whiteListDomainList = serviceConfig.getWhiteListDomainList();
        if(whiteListDomainList == null || whiteListDomainList.isEmpty()){
            log.info("系统未设置【白名单】，请先前往设置");
            this.insertSystemRecord(systemAccessRecordEntity,ErrorEnum.ILLEGAL_DOMAIN);
            response.getWriter().write(JSON.toJSONString(ResultVOUtil.error(ErrorEnum.ILLEGAL_DOMAIN)));
            response.getWriter().flush();
            return;
        }

        String serverName = request.getServerName();
        log.info("【serverName】: {}",serverName);
        
        if(StringUtils.isBlank(serverName)){
            log.info("访问的域名为空,验证不通过");
            this.insertSystemRecord(systemAccessRecordEntity,ErrorEnum.ILLEGAL_DOMAIN);
            response.getWriter().write(JSON.toJSONString(ResultVOUtil.error(ErrorEnum.ILLEGAL_DOMAIN)));
            response.getWriter().flush();
            return;
        }else if(!checkDomain(serverName,whiteListDomainList)){
            log.info("访问的域名未在系统白名单内,【serverName】: {}",serverName);
            this.insertSystemRecord(systemAccessRecordEntity,ErrorEnum.ILLEGAL_DOMAIN);
            response.getWriter().write(JSON.toJSONString(ResultVOUtil.error(ErrorEnum.ILLEGAL_DOMAIN)));
            response.getWriter().flush();
            return;
        }else{
            log.info("访问的域名在系统【白名单】中");
        }
        
        if(StringUtils.equals(OPTIONS_METHOD,requestMethod)){
            log.info("请求方法为【OPTIONS】,请求成功");
            this.insertSystemRecord(systemAccessRecordEntity,ErrorEnum.SUCCESS);
            response.getWriter().write(JSON.toJSONString(ResultVOUtil.success()));
            response.getWriter().flush();
            return;
        }else{
            boolean urlNeedCheck = true;
            if(!cacheUrlBuffer.contains(servletPath)){
                List<String> exculdeUtl = null;
                TokenConfig.Exclude exclude = tokenConfig.getExclude();
                if(exclude != null){
                    exculdeUtl = exclude.getUrls();
                }
                if(exculdeUtl != null && !exculdeUtl.isEmpty()){
                    for (int cnt = 0;cnt < exculdeUtl.size();cnt ++){
                        String url = exculdeUtl.get(cnt);
                        if(StringUtils.contains(servletPath,url)){
                            urlNeedCheck = false;
                            break;
                        }
                    }
                }
            }
            
            if(urlNeedCheck){
                if(!cacheUrlBuffer.contains(servletPath)){
                    cacheUrlBuffer.add(servletPath);
                }

                Cookie cookie = CookieUtil.getCookie(request, tokenConfig.getCookieName());
                if(cookie == null){
                    log.info("未获取到相关cookieName:{},cookie获取失败",tokenConfig.getCookieName());
                    this.insertSystemRecord(systemAccessRecordEntity,ErrorEnum.COOKIE_IS_NULL);
                    response.getWriter().write(JSON.toJSONString(ErrorEnum.COOKIE_IS_NULL));
                    response.getWriter().flush();
                    return;
                }else{
                    String token = cookie.getValue();

                    ResultVO<CurrentUser> result = TokenUtil.verifyToken(token);
                    
                    if(result == null || !ResultVOUtil.checkResultSuccess(result)){
                        log.info("请求token验证失败");
                        Date endDate = new Date();
                        systemAccessRecordEntity.setRequestEndTime(endDate);
                        systemAccessRecordEntity.setResponseCode(result == null ? null : result.getCode());
                        systemAccessRecordEntity.setResponseMsg(result == null ? null : result.getMsg());
                        systemAccessRecordEntity.setProcessingTime(Math.abs(endDate.getTime() - systemAccessRecordEntity.getRequestStartTime().getTime()));
                        systemAccessRecordService.save(systemAccessRecordEntity);

                        response.getWriter().write(JSON.toJSONString(result));
                        response.getWriter().flush();
                        return;
                    }else{
                        CurrentUser currentUser = result.getData();
                        CookieUtil.setCookie(response,currentUser);
                        systemAccessRecordEntity.setUserId(currentUser.getId());
                        request.getSession().setAttribute(Repository.REQUEST_ATTRIBUTE.CURRENT_LOGIN_USER,currentUser);
                    }
                }
            }
            this.insertSystemRecord(systemAccessRecordEntity,ErrorEnum.SUCCESS);
            filterChain.doFilter(request,response);
        }
        log.info("<============AuthFilter<--------doFilter 【E N D】");
    }
    
    public void insertSystemRecord(SystemAccessRecordEntity entity,ErrorEnum errorEnum){
        Date endDate = new Date();
        entity.setRequestEndTime(endDate);
        entity.setResponseCode(errorEnum.getCode());
        entity.setResponseMsg(errorEnum.getMsg());
        entity.setProcessingTime(Math.abs(endDate.getTime() - entity.getRequestStartTime().getTime()));
        systemAccessRecordService.save(entity);
    }
    
    public boolean checkDomain(String serverName,List<String> checkList){
        boolean flag = false;
        if(StringUtils.isBlank(serverName) || checkList == null || checkList.isEmpty()){
            return flag;
        }
        
        String whiteDomain = null;
        for (int cnt = 0;cnt < checkList.size();cnt ++){
            whiteDomain = checkList.get(cnt);
            if(StringUtils.contains(whiteDomain,serverName)){
                flag = true;
                break;
            }
        }
        
        return flag;
    }
    
}
