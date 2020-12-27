package com.fh.authorization;

import com.fh.login.LoginJwtUtil;
import com.fh.utils.CommonsReturn;
import com.fh.utils.ReturnCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LoginAuthorizationAop
 * @Deacription TODO
 * @Author 路东生
 * @Date 2020/12/6 19:23
 **/
/*@Aspect
@Component
@Order(4)*/
public class LoginAuthorizationAop {
    @Autowired
    HttpServletRequest request;
    @Autowired
    LoginJwtUtil loginJwtUtil;
    @Resource
    RedisTemplate redisTemplate;

    public static final String pre_token="ACCESS_TOKEN:";

    @Around(value = "execution(* com.fh.*.controller..*.*(..)) && @annotation(loginAuthorization)")
    public Object loginAround(ProceedingJoinPoint joinPoint, LoginAuthorization loginAuthorization) throws Throwable {
        //从头部信息中获取token
        String token=request.getHeader("Authorization-token");
        if(StringUtils.isBlank(token)){
            return CommonsReturn.error(ReturnCode.LOGIN_DISABLED);
        }
        //进行解析校验
        CommonsReturn commonsReturn=loginJwtUtil.authToken(token);
        if(commonsReturn.getCode()!=200){
            return commonsReturn;
        }

        //获取用户信息
        Claims claims= (Claims) commonsReturn.getData();
        String userId=String.valueOf(claims.get("userId"));
        String accessKey=pre_token+userId+":"+token;
        if(!redisTemplate.hasKey(accessKey)){
            return commonsReturn.error(ReturnCode.LOGIN_DISABLED);
        }
        Long currentTime=System.currentTimeMillis();
        //续签
        redisTemplate.opsForValue().set(accessKey,currentTime);
        redisTemplate.expire(accessKey,2,TimeUnit.MINUTES);

        Object obj=null;
        try {
            obj=joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw  throwable;
        }

        return obj;

    }

}
