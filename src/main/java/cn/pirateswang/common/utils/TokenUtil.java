package cn.pirateswang.common.utils;

import cn.pirateswang.common.config.TokenConfig;
import cn.pirateswang.common.publicEnum.ErrorEnum;
import cn.pirateswang.common.publicVO.CurrentUser;
import cn.pirateswang.common.publicVO.ResultVO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {
    
    private static Logger log = LoggerFactory.getLogger(TokenUtil.class);
    
    @Autowired
    private TokenConfig tokenConfig;
    
    private static TokenUtil self;
    
    @PostConstruct
    public void init(){
        self = this;
    }

    public static String getToken(CurrentUser user){
        Map<String, Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");
        //token过期时间 单位：秒
        Integer effectiveTime = self.tokenConfig.getEffectiveTime();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,effectiveTime);
        Date expiresTime = calendar.getTime();
        
        String token = StringUtils.EMPTY;
        try {
            token = JWT.create().withHeader(map) //签名算法信息
                    .withIssuedAt(new Date()) //生效开始时间
                    .withExpiresAt(expiresTime) //过期时间
                    .withClaim("id",user.getId()) //记录业务信息
                    .withClaim("userName",user.getUserName())
                    .withClaim("age",user.getAge())
                    .sign(Algorithm.HMAC256(self.tokenConfig.getCookieSecrete())); //附加加密信息
        } catch (Exception e) {
            log.error("【生成token异常】, exception:{}",e);
        }
        return token;
    }
    
    
    /**
     * 验证token信息
     * @param token
     * @return
     */
    public static ResultVO<CurrentUser> verifyToken(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(self.tokenConfig.getCookieSecrete())).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claimMap = jwt.getClaims();
            CurrentUser currentUser = new CurrentUser();
            for (String key: claimMap.keySet()){
                Claim claim = claimMap.get(key);
                if("id".equals(key)){
                    currentUser.setId(claim.asLong());
                }else if("userName".equals(key)){
                    currentUser.setUserName(claim.asString());
                }else if("age".equals(key)){
                    currentUser.setAge(claim.asInt());
                }
            }
            return ResultVOUtil.success(currentUser);
        } catch (SignatureVerificationException | JWTDecodeException ve) {
            //token验证不通过
            log.error("【TOKEN验证不通过】,token:{}",token);
            return ResultVOUtil.error(ErrorEnum.TOKEN_VERIFY_FAILURE);
        } catch (TokenExpiredException te){
            //token过期
            log.error("【TOKEN已过期】,token:{}",token);
            return ResultVOUtil.error(ErrorEnum.TOKEN_OVERDUE);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            //token验证异常
            log.error("【TOKEN验证异常】,token:{}",token);
            return ResultVOUtil.error(ErrorEnum.TOKEN_VERIFY_EXCEPTION);
        } catch (Exception e){
            log.error("【TOKEN验证异常,exception】,token:{},exception:{}",token,e);
            return ResultVOUtil.error(ErrorEnum.TOKEN_VERIFY_EXCEPTION);
        }

    }
    
}
