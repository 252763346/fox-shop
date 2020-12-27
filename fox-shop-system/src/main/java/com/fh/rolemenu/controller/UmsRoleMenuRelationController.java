package com.fh.rolemenu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.role.service.IUmsRoleService;
import com.fh.rolemenu.entity.RoleMenuBo;
import com.fh.rolemenu.entity.UmsRoleMenuRelation;
import com.fh.rolemenu.service.IUmsRoleMenuRelationService;
import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台角色菜单关系表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/rolemenu")
public class UmsRoleMenuRelationController {
    @Resource
    IUmsRoleMenuRelationService umsRoleMenuRelationService;

    //菜单保存
    @PostMapping
    public CommonsReturn saveOrUpdateRoleMenu(@RequestParam Long roleId,@RequestParam List<Long> menuIds){
        //先删除
        QueryWrapper<UmsRoleMenuRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        umsRoleMenuRelationService.remove(queryWrapper);
        //再新增
        List<UmsRoleMenuRelation> list=new ArrayList<>();
        for (int i = 0; i <menuIds.size() ; i++) {
            UmsRoleMenuRelation umsRoleMenuRelation=new UmsRoleMenuRelation();
            umsRoleMenuRelation.setRoleId(roleId);
            umsRoleMenuRelation.setMenuId(menuIds.get(i));
            list.add(umsRoleMenuRelation);
        }
        umsRoleMenuRelationService.saveBatch(list);
        return CommonsReturn.success();
    }

    //菜单回显
    @GetMapping
    public CommonsReturn queryMenuByroleId(Long roleId){
        List<UmsRoleMenuRelation> menuIdList = umsRoleMenuRelationService.list(new QueryWrapper<UmsRoleMenuRelation>().eq("role_id", roleId));
        return CommonsReturn.success(menuIdList);
    }

}
