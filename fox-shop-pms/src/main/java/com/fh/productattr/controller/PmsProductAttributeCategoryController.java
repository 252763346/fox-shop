package com.fh.productattr.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.category.service.IPmsProductCategoryService;
import com.fh.productattr.entity.PmsProductAttributeCategory;
import com.fh.productattr.service.IPmsProductAttributeCategoryService;
import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/productattr")
public class PmsProductAttributeCategoryController {
    @Resource
    IPmsProductAttributeCategoryService pmsProductAttributeCategoryService;

    //回显
    @GetMapping("huixian")
    public CommonsReturn queryById(Integer id){
        PmsProductAttributeCategory pmsProductAttributeCategory = pmsProductAttributeCategoryService.getById(id);
        return CommonsReturn.success(pmsProductAttributeCategory);
    }

    //新增/修改
    @PostMapping
    public CommonsReturn saveOrUpdateAttrCategory(PmsProductAttributeCategory pmsProductAttributeCategory){
        pmsProductAttributeCategoryService.saveOrUpdate(pmsProductAttributeCategory);
        return CommonsReturn.success();
    }

    //分页查询
    @GetMapping
    public CommonsReturn queryAttrCategory(Page<PmsProductAttributeCategory> page){
        IPage<PmsProductAttributeCategory> iPage=pmsProductAttributeCategoryService.page(page);
        return CommonsReturn.success(iPage);
    }

}
