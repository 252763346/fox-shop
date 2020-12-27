package com.fh.role.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.role.entity.UmsRole;
import com.fh.role.service.IUmsRoleService;
import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台用户角色表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-16
 */
@RestController
@RequestMapping("/role")
public class UmsRoleController {
    @Resource
    IUmsRoleService roleService;


    //查询所有角色
    @GetMapping("all")
    public CommonsReturn queryRoleAll(){
        List<UmsRole> list = roleService.list();
        return CommonsReturn.success(list);
    }

    //回显
    @GetMapping("huixian")
    public CommonsReturn queryRoleById(Integer id){
        UmsRole umsRole = roleService.getById(id);
        return CommonsReturn.success(umsRole);
    }

    //新增/修改
    @PostMapping
    public CommonsReturn saveOrUpdateRole(UmsRole umsRole){
        if(umsRole.getId()==null){
            umsRole.setCreateTime(new Date());
            roleService.saveOrUpdate(umsRole);
        }
        roleService.saveOrUpdate(umsRole);
        return CommonsReturn.success();
    }

    //分页查询
    @GetMapping
    public CommonsReturn queryRoleListData(Page<UmsRole> page, String roleName){
        QueryWrapper<UmsRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",roleName);
        IPage<UmsRole> ipage=roleService.page(page,queryWrapper);
        return CommonsReturn.success(ipage);
    }

}
