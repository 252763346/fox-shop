package com.fh.userrole.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.userrole.entity.UmsAdminRoleRelation;
import com.fh.userrole.service.IUmsAdminRoleRelationService;
import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 后台用户和角色关系表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/userrole")
public class UmsAdminRoleRelationController {
    @Resource
    IUmsAdminRoleRelationService umsAdminRoleRelationService;

    //回显用户角色
    @GetMapping
    public CommonsReturn queryRoleByUserId(Long userId){
        List<UmsAdminRoleRelation> roleIdList = umsAdminRoleRelationService.list(new QueryWrapper<UmsAdminRoleRelation>().eq("admin_id", userId));
        return CommonsReturn.success(roleIdList);
    }

    //保存用户角色
    @PostMapping
    public CommonsReturn saveOrUpdateUserRole(@RequestParam Long userId, @RequestParam List<Long> roleIds){
        //先删除
        QueryWrapper<UmsAdminRoleRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("admin_id",userId);
        umsAdminRoleRelationService.remove(queryWrapper);
        //再新增
        List<UmsAdminRoleRelation> list=new ArrayList<>();
        for (int i = 0; i <roleIds.size() ; i++) {
            UmsAdminRoleRelation umsAdminRoleRelation=new UmsAdminRoleRelation();
            umsAdminRoleRelation.setAdminId(userId);
            umsAdminRoleRelation.setRoleId(roleIds.get(i));
            list.add(umsAdminRoleRelation);
        }
        umsAdminRoleRelationService.saveBatch(list);
        return CommonsReturn.success();
    }

}
