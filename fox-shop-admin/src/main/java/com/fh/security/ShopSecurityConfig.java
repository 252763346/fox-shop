package com.fh.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.admin.entity.UmsAdmin;
import com.fh.admin.service.IUmsAdminService;
import com.fh.resource.entity.UmsResource;
import com.fh.role.entity.UmsRole;
import com.fh.security.bo.AdminUserDetails;
import com.fh.security.filter.JwtAuthenticationTokenFilter;
import com.fh.security.filter.RestAuthenticationEntryPoint;
import com.fh.security.filter.RestfulAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.management.relation.RoleList;
import java.util.List;

/**
 * @ClassName ShopSecurityConfig
 * @Deacription TODO
 * @Author 路东生
 * @Date 2020/12/20 12:16
 **/
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ShopSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    IUmsAdminService adminService;
    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    RestfulAccessDeniedHandler restfulAccessDeniedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login/**","/UploadFileController").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
        //禁用缓存
        http.headers().cacheControl();
        //自定义拦截器加载过来
        http.addFilterBefore(jwtAuthenticationTokenFilter(),UsernamePasswordAuthenticationFilter.class);
        //自定义未登录或者未授权的结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);

    }

    //用户登录认证和权限授权
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }


    //注入登录拦截器
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }


    //根据用户名获取用户信息和权限
    @Bean
    public UserDetailsService userDetailsService(){
        return username->{
            UmsAdmin admin = adminService.getUserByName(username);
            if(admin!=null){
                //获取权限
                List<UmsResource> resourceList=adminService.queryResourceByAdmin(admin.getId(),username);
                //获取角色
                List<UmsRole> RoleList=adminService.queryRoleByAdmin(admin.getId(),username);
                return new AdminUserDetails(admin,resourceList,RoleList);
            }
            throw new UsernameNotFoundException("用户名不存在");
        };

    }

    //加密方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
