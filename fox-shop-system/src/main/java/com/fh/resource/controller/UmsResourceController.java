package com.fh.resource.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.resource.entity.UmsResource;
import com.fh.resource.entity.searchBean;
import com.fh.resource.service.IUmsResourceService;
import com.fh.role.entity.UmsRole;
import com.fh.utils.CommonsReturn;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台资源表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/resource")
public class UmsResourceController {
    @Resource
    IUmsResourceService umsResourceService;

    //查询所有资源
    @GetMapping("all")
    public CommonsReturn fetchAllResourceList(){
        List<UmsResource> resourceList = umsResourceService.list();
        return CommonsReturn.success(resourceList);
    }

    //回显
    @GetMapping("huixian")
    public CommonsReturn queryResourceById(Integer id){
        UmsResource umsResource = umsResourceService.getById(id);
        return CommonsReturn.success(umsResource);
    }

    //新增/修改
    @PostMapping
    public CommonsReturn saveOrUpdateResource(UmsResource umsResource){
        umsResourceService.saveOrUpdate(umsResource);
        return CommonsReturn.success();
    }

    //动态下拉框查询
    @GetMapping("select")
    public CommonsReturn querySelect(){
        List<UmsResource> umsResources = umsResourceService.list();
        return CommonsReturn.success(umsResources);
    }

    //分页查询
    @GetMapping
    public CommonsReturn queryresourceListData(Page<UmsResource> umsResourcePage, searchBean searchBean){
        QueryWrapper<UmsResource> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(searchBean.getNameKeyword())){
            queryWrapper.like("name",searchBean.getNameKeyword());
        }
        if(StringUtils.isNotBlank(searchBean.getUrlKeyword())){
            queryWrapper.like("url",searchBean.getUrlKeyword());
        }
        if(searchBean.getCategoryId()!=null){
            queryWrapper.eq("category_id",searchBean.getCategoryId());
        }
        IPage<UmsResource> iPage=umsResourceService.page(umsResourcePage,queryWrapper);
        return CommonsReturn.success(iPage);
    }

}
