package cn.pirateswang.common.filter;

import cn.pirateswang.common.config.ServiceConfig;
import cn.pirateswang.common.config.TokenConfig;
import cn.pirateswang.common.publicEnum.ErrorEnum;
import cn.pirateswang.common.publicVO.CurrentUser;
import cn.pirateswang.common.publicVO.ResultVO;
import cn.pirateswang.common.utils.*;
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
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("============>AuthFilter-------->doFilter 【START】");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
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

        if(StringUtils.isBlank(ip)){
            log.info("访问IP为空,验证不通过");
            response.getWriter().write(JSON.toJSONString(ResultVOUtil.error(ErrorEnum.ILLEGAL_VISIT)));
            response.getWriter().flush();
            return;
        }
        
        //系统访问黑名单设置
        List<String> blackVisitList = serviceConfig.getBlackVisitList();
        if(blackVisitList != null && !blackVisitList.isEmpty()){
            log.info("系统有设置【黑名单】");
            if(checkDomain(ip,blackVisitList)){
                log.info("该访问【IP】: {}，被系统限制访问",ip);
                response.getWriter().write(JSON.toJSONString(ResultVOUtil.error(ErrorEnum.ILLEGAL_VISIT)));
                response.getWriter().flush();
                return;
            }else{
                log.info("访问IP未在系统设置【黑名单】内");
            }
        }


        //系统白名单设置
        List<String> whiteListDomainList = serviceConfig.getWhiteListDomainList();
        if(whiteListDomainList == null || whiteListDomainList.isEmpty()){
            log.info("系统未设置【白名单】，请先前往设置");
            response.getWriter().write(JSON.toJSONString(ResultVOUtil.error(ErrorEnum.ILLEGAL_DOMAIN)));
            response.getWriter().flush();
            return;
        }

        String serverName = request.getServerName();
        log.info("【serverName】: {}",serverName);
        
        if(StringUtils.isBlank(serverName)){
            log.info("访问的域名为空,验证不通过");
            response.getWriter().write(JSON.toJSONString(ResultVOUtil.error(ErrorEnum.ILLEGAL_DOMAIN)));
            response.getWriter().flush();
            return;
        }else if(!checkDomain(serverName,whiteListDomainList)){
            log.info("访问的域名未在系统白名单内,【serverName】: {}",serverName);
            response.getWriter().write(JSON.toJSONString(ResultVOUtil.error(ErrorEnum.ILLEGAL_DOMAIN)));
            response.getWriter().flush();
            return;
        }else{
            log.info("访问的域名在系统【白名单】中");
        }
        
        if(StringUtils.equals(OPTIONS_METHOD,requestMethod)){
            log.info("请求方法为【OPTIONS】,请求成功");
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
                    response.getWriter().write(JSON.toJSONString(ErrorEnum.COOKIE_IS_NULL));
                    response.getWriter().flush();
                    return;
                }else{
                    String token = cookie.getValue();

                    ResultVO<CurrentUser> result = TokenUtil.verifyToken(token);
                    
                    if(result == null || !ResultVOUtil.checkResultSuccess(result)){
                        log.info("请求token验证失败");
                        response.getWriter().write(JSON.toJSONString(result));
                        response.getWriter().flush();
                        return;
                    }else{
                        CurrentUser currentUser = result.getData();
                        CookieUtil.setCookie(response,currentUser);
                        request.getSession().setAttribute(Repository.REQUEST_ATTRIBUTE.CURRENT_LOGIN_USER,currentUser);
                        filterChain.doFilter(request,response);
                    }
                }
            }
            filterChain.doFilter(request,response);
        }
        log.info("<============AuthFilter<--------doFilter 【E N D】");
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
