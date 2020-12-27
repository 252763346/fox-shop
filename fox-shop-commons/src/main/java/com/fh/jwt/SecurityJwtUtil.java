package com.fh.jwt;

import com.fh.utils.CommonsReturn;
import com.fh.utils.ReturnCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoginJwtUtil
 * @Deacription TODO
 * @Author 路东生
 * @Date 2020/12/6 18:40
 **/
@Component
public class SecurityJwtUtil {
    @Value("${user.jwtset.signature}")
    private String signature;
    @Value("${user.jwtset.exptime}")
    private Long exptime;
    @Value("${user.jwtset.alg}")
    private String alg;
    @Value("${user.jwtset.type}")
    private String type;

    //创建token
    public String createToken(UserDetails userDetails){
        Map<String,Object> headerMap=new HashMap<>();
        //头部信息
        headerMap.put("alg",alg);
        headerMap.put("type",type);

        //有效载荷
        Map<String,Object> payloadMap=new HashMap<>();
        payloadMap.put("username",userDetails.getUsername());
        payloadMap.put("createtime",new Date());

        //失效时间
        Long startTime= System.currentTimeMillis();
        Long endTime=startTime+exptime;

        //生成jwt
        String token= Jwts.builder()
                .setHeader(headerMap)
                .setClaims(payloadMap)
                .setExpiration(new Date(endTime))
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        return token;
    }

    //解析token
    public CommonsReturn authToken(String token){
        try {
            Claims claims=Jwts.parser()
                    .setSigningKey(signature)
                    .parseClaimsJws(token)
                    .getBody();
            return CommonsReturn.success(claims);
        }catch (Exception e){
            e.printStackTrace();
            return CommonsReturn.error(ReturnCode.LOGIN_DISABLED);
        }
    }


    //获取用户名称
    public String getUsername(String token){
        try {
            Claims claims=Jwts.parser()
                    .setSigningKey(signature)
                    .parseClaimsJws(token)
                    .getBody();
            return (String) claims.get("username");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
