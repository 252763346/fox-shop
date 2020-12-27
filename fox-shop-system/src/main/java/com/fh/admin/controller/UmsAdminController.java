package com.fh.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.admin.entity.UmsAdmin;
import com.fh.admin.service.IUmsAdminService;
import com.fh.utils.CommonsReturn;
import com.fh.utils.MD5Util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    @Resource
    IUmsAdminService adminService;

    //回显
    @GetMapping("huixian")
    public CommonsReturn queryUserById(Integer id){
        UmsAdmin umsAdmin = adminService.getById(id);
        return CommonsReturn.success(umsAdmin);
    }


    //新增/修改
    @PostMapping
    public CommonsReturn saveOrUpdateUser(UmsAdmin umsAdmin){
        if(umsAdmin.getId()!=null){
            adminService.saveOrUpdate(umsAdmin);
        }else{
            umsAdmin.setCreateTime(new Date());
            umsAdmin.setPassword(new BCryptPasswordEncoder().encode("123456"));
            adminService.saveOrUpdate(umsAdmin);
        }
        return CommonsReturn.success();
    }


    //分页查询
    @GetMapping
    public CommonsReturn queryUserListData(Page<UmsAdmin> page,String keywords){
        QueryWrapper<UmsAdmin> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("username", keywords).or().like("nick_name", keywords);
        IPage<UmsAdmin> ipage=adminService.page(page,queryWrapper);
        return CommonsReturn.success(ipage);
    }

}
