package com.fh.security.filter;

import com.fh.jwt.SecurityJwtUtil;
import com.fh.utils.CommonsReturn;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Deacription 自定义拦截器
 * @Author 路东生
 * @Date 2020/12/20 19:42
 **/
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    SecurityJwtUtil securityJwtUtil;
    @Autowired
    UserDetailsService userDetailsService;
    @Resource
    RedisTemplate redisTemplate;

    public static final String pre_token="ACCESS_TOKEN:";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token=request.getHeader("Authorization-token");
        if(StringUtils.isNotBlank(token)){
            //验证token值有效还是无效
            CommonsReturn commonsReturn = securityJwtUtil.authToken(token);
            if(commonsReturn.getCode()==200 && SecurityContextHolder.getContext().getAuthentication()==null){
                String username = securityJwtUtil.getUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                String accessKey=pre_token+userDetails.getUsername()+":"+token;
                if(redisTemplate.hasKey(accessKey)){
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    //续签
                    Long currentTime=System.currentTimeMillis();
                    redisTemplate.opsForValue().set(accessKey,currentTime);
                    redisTemplate.expire(accessKey,2, TimeUnit.HOURS);
                }

            }

        }
        filterChain.doFilter(request,response);
    }

}
