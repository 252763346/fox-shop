package com.fh.roleresource.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.roleresource.entity.UmsRoleResourceRelation;
import com.fh.roleresource.service.IUmsRoleResourceRelationService;
import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 后台角色资源关系表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/roleresource")
public class UmsRoleResourceRelationController {
    @Resource
    IUmsRoleResourceRelationService umsRoleResourceRelationService;

    //资源新增
    @PostMapping
    public CommonsReturn saveOrUpdateResource(@RequestParam Long roleId,@RequestParam List<Long> resourceIds){
        //先删除
        umsRoleResourceRelationService.remove(new QueryWrapper<UmsRoleResourceRelation>().eq("role_id",roleId));
        //再增加
        List<UmsRoleResourceRelation> list=new ArrayList<>();
        for (int i = 0; i <resourceIds.size() ; i++) {
            UmsRoleResourceRelation umsRoleResourceRelation=new UmsRoleResourceRelation();
            umsRoleResourceRelation.setRoleId(roleId);
            umsRoleResourceRelation.setResourceId(resourceIds.get(i));
            list.add(umsRoleResourceRelation);
        }
        umsRoleResourceRelationService.saveBatch(list);
        return CommonsReturn.success();
    }

    //资源回显
    @GetMapping
    public CommonsReturn listResourceByRole(Long roleId){
        List<UmsRoleResourceRelation> resourceList = umsRoleResourceRelationService.list(new QueryWrapper<UmsRoleResourceRelation>().eq("role_id", roleId));
        return CommonsReturn.success(resourceList);
    }

}
