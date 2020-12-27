package com.fh.menu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.jwt.SecurityJwtUtil;
import com.fh.menu.entity.UmsMenu;
import com.fh.menu.service.IUmsMenuService;
import com.fh.role.entity.UmsRole;
import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台菜单表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/menu")
public class UmsMenuController {
    @Resource
    IUmsMenuService menuService;
    @Resource
    HttpServletRequest request;
    @Resource
    SecurityJwtUtil securityJwtUtil;

    //查询动态菜单栏
    @GetMapping("queryMenuData")
    public CommonsReturn queryMenuTreeList(){
        String token = request.getHeader("Authorization-token");
        String username = securityJwtUtil.getUsername(token);
        List<Map<String,Object>> menuList=menuService.queryMenuTreeList(username);
        return CommonsReturn.success(menuList);
    }

    //查询菜单
    @GetMapping("queryMenuTree")
    public CommonsReturn queryTreeList(){
        List<Map<String,Object>> treeList = menuService.queryTreeList();
       return CommonsReturn.success(treeList);
    }



    //回显
    @GetMapping("huixian")
    public CommonsReturn queryMenuById(Integer id){
        UmsMenu umsMenu = menuService.getById(id);
        return CommonsReturn.success(umsMenu);
    }

    //新增/修改
    @PostMapping
    public CommonsReturn saveOrUpdateMenu(UmsMenu umsMenu){
        menuService.saveOrUpdate(umsMenu);
        return CommonsReturn.success();
    }

    //动态下拉框查询
    @GetMapping("select")
    public CommonsReturn querySelect(Integer level){
        QueryWrapper<UmsMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("level",level);
        List<UmsMenu> umsMenus = menuService.list(queryWrapper);
        return CommonsReturn.success(umsMenus);
    }

    //分页查询
    @GetMapping
    public CommonsReturn queryMenuListData(Page<UmsMenu> umsMenuPage,Integer parentId){
        QueryWrapper<UmsMenu> queryWrapper=new QueryWrapper<>();
        if(parentId!=null){
            queryWrapper.eq("level",1);
            queryWrapper.eq("parent_id",parentId);
        }else{
            queryWrapper.eq("level",0);
        }
        IPage<UmsMenu> ipage=menuService.page(umsMenuPage,queryWrapper);
        return CommonsReturn.success(ipage);
    }

}
