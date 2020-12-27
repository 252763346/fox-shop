package com.fh.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fh.admin.entity.UmsAdmin;
import com.fh.admin.mapper.UmsAdminMapper;
import com.fh.admin.service.IUmsAdminService;
import com.fh.jwt.SecurityJwtUtil;
import com.fh.resource.entity.UmsResource;
import com.fh.role.entity.UmsRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author lds
 * @since 2020-12-07
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements IUmsAdminService {
    @Resource
    UmsAdminMapper adminMapper;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SecurityJwtUtil securityJwtUtil;
    @Autowired
    RedisTemplate redisTemplate;

    public static final String pre_token="ACCESS_TOKEN:";
    public static final String admin_login="LOGIN:";


    @Override
    public List<UmsResource> queryResourceByAdmin(Long id,String username) {
        //根据key从redis中取
        String key=admin_login+username+":RESOURCE";
        if(redisTemplate.hasKey(key)){
            return (List<UmsResource>) redisTemplate.opsForValue().get(key);
        }
        //redis中没有则去数据库中取
        List<UmsResource> list=adminMapper.queryResourceByAdmin(id);
        redisTemplate.opsForValue().set(key,list);
        redisTemplate.expire(key,2, TimeUnit.HOURS);
        return list;
    }

    @Override
    public List<UmsRole> queryRoleByAdmin(Long id,String username) {
        //根据key从redis中取
        String key=admin_login+username+":ROLE";
        if(redisTemplate.hasKey(key)){
            return (List<UmsRole>) redisTemplate.opsForValue().get(key);
        }
        //redis中没有则去数据库中取
        List<UmsRole> list=adminMapper.queryRoleByAdmin(id);
        redisTemplate.opsForValue().set(key,list);
        redisTemplate.expire(key,2, TimeUnit.HOURS);
        return list;
    }

    @Override
    public UmsAdmin getUserByName(String username) {
        QueryWrapper<UmsAdmin> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        String key=admin_login+username+":USER";
        if(redisTemplate.hasKey(key)){
            return (UmsAdmin) redisTemplate.opsForValue().get(key);
        }
        UmsAdmin admin=getOne(queryWrapper);
        redisTemplate.opsForValue().set(key,admin);
        redisTemplate.expire(key,2, TimeUnit.HOURS);
        return admin;
    }

    //退出登录
    @Override
    public void logout(String userName) {
        clearAdminRedis(userName);
    }

    @Override
    public String login(String userName, String password) {
        String token=null;
        try {
                //清理之前的用户登录信息
                clearAdminRedis(userName);
            //触发登录认证的方法
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            //对比密码是否一致
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("用户密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            //上下文可以获取到该对象
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //生成token值
            token=securityJwtUtil.createToken(userDetails);

            Long currentTime=System.currentTimeMillis();
            String accessKey=pre_token+userDetails.getUsername()+":"+token;
            redisTemplate.opsForValue().set(accessKey,currentTime);
            redisTemplate.expire(accessKey,2, TimeUnit.HOURS);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return token;
    }

    public void clearAdminRedis(String userName){
        //清理之前的token信息
        String tokenKey=pre_token+userName+":*";
        Set<String> tokenKeys = redisTemplate.keys(tokenKey);
        if(!CollectionUtils.isEmpty(tokenKeys)){
            redisTemplate.delete(tokenKeys);
        }
        //清理上次登录的用户信息
        String adminKey=admin_login+userName+":*";
        Set<String> keys = redisTemplate.keys(adminKey);
        if(!CollectionUtils.isEmpty(keys)){
            redisTemplate.delete(keys);
        }
    }


}
