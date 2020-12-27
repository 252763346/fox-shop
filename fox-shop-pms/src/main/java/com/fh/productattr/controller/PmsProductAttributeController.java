package com.fh.productattr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.productattr.AttrSearch;
import com.fh.productattr.entity.PmsProductAttribute;
import com.fh.productattr.service.IPmsProductAttributeService;
import com.fh.utils.CommonsReturn;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/attribute")
public class PmsProductAttributeController {
    @Resource
    IPmsProductAttributeService pmsProductAttributeService;

    //回显
    @GetMapping("huixian")
    public CommonsReturn huixian(Integer id){
        PmsProductAttribute pmsProductAttribute = pmsProductAttributeService.getById(id);
        return CommonsReturn.success(pmsProductAttribute);
    }

    //新增
    @PostMapping
    public CommonsReturn saveOrUpdateAttrList(PmsProductAttribute pmsProductAttribute){
        pmsProductAttributeService.saveOrUpdate(pmsProductAttribute);
        return CommonsReturn.success();
    }

    //分页查询
    @GetMapping
    public CommonsReturn queryPageData(Page<PmsProductAttribute> page, AttrSearch search){
        QueryWrapper<PmsProductAttribute> queryWrapper=new QueryWrapper<PmsProductAttribute>();
        if(StringUtils.isNotBlank(search.getName())){
            queryWrapper.like("name",search.getName());
        }
        queryWrapper.eq("product_attribute_category_id",search.getCid());
        queryWrapper.eq("type",search.getType());
        queryWrapper.orderByAsc("sort");
        IPage<PmsProductAttribute> iPage=pmsProductAttributeService.page(page,queryWrapper);
        return CommonsReturn.success(iPage);
    }

}
