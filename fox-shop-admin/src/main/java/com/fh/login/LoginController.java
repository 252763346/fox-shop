package com.fh.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.admin.entity.UmsAdmin;
import com.fh.admin.service.IUmsAdminService;
import com.fh.jwt.SecurityJwtUtil;
import com.fh.utils.CommonsReturn;
import com.fh.utils.MD5Util;
import com.fh.utils.ReturnCode;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LoginController
 * @Deacription TODO
 * @Author 路东生
 * @Date 2020/12/6 14:10
 **/
@RestController
@RequestMapping("/login")
@Api(tags = "LoginController",description = "用户登录")
public class LoginController {
    @Resource
    IUmsAdminService adminService;
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    HttpServletRequest request;
    @Resource
    SecurityJwtUtil securityJwtUtil;

    public static final String pre_token="ACCESS_TOKEN:";

    @PostMapping
    public CommonsReturn doLogin(@RequestParam("userName") String userName,@RequestParam("password") String password){
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
            return CommonsReturn.error(ReturnCode.USERNAME_PASSWORD_NULL);
        }
        //登录认证
        String token=adminService.login(userName,password);
        if(token==null){
            return CommonsReturn.error(ReturnCode.LOGIN_ERROR);
        }

       /* //根据用户名查询用户信息
        UmsAdmin user = adminService.getOne(new QueryWrapper<UmsAdmin>().eq("userName",userName));
        //判断用户账号密码是否为空
        if(user==null){
            return CommonsReturn.error(ReturnCode.USERNAME_NOEXIST);
        }
        //判断用户密码是否正确
        String MD5password = MD5Util.MD5Encode(password);
        if(!MD5password.equals(user.getPassword())){
            return CommonsReturn.error(ReturnCode.PASSWORD_ERROR);
        }

        String token=loginJwtUtil.createToken(user);
        Long currentTime=System.currentTimeMillis();
        String accessKey=pre_token+user.getId()+":"+token;
        redisTemplate.opsForValue().set(accessKey,currentTime);
        redisTemplate.expire(accessKey,2,TimeUnit.MINUTES);*/
        return CommonsReturn.success(token);
    }

    //退出登录
    @GetMapping
    public CommonsReturn logout(){
        String token = request.getHeader("Authorization-token");
        String userName = securityJwtUtil.getUsername(token);
        adminService.logout(userName);
        return CommonsReturn.success();
    }

}
