package com.fh.resourcecategory.controller;


import com.fh.resourcecategory.entity.UmsResourceCategory;
import com.fh.resourcecategory.service.IUmsResourceCategoryService;
import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资源分类表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/resourcecategory")
public class UmsResourceCategoryController {
    @Resource
    IUmsResourceCategoryService umsResourceCategoryService;

    //删除
    @DeleteMapping
    public CommonsReturn deleteCategory(Integer id){
        umsResourceCategoryService.removeById(id);
        return CommonsReturn.success();
    }

    //回显
    @GetMapping("huixian")
    public CommonsReturn queryCategoryById(Integer id){
        UmsResourceCategory category = umsResourceCategoryService.getById(id);
        return CommonsReturn.success(category);
    }

    //新增/修改
    @PostMapping
    public CommonsReturn saveOrUpdateCategory(UmsResourceCategory umsResourceCategory){
        if(umsResourceCategory.getId()==null){
            umsResourceCategory.setCreateTime(new Date());
            umsResourceCategoryService.saveOrUpdate(umsResourceCategory);
        }
        umsResourceCategoryService.saveOrUpdate(umsResourceCategory);
        return CommonsReturn.success();
    }

    //查询
    @GetMapping
    public CommonsReturn queryCategoryListData(){
        List<UmsResourceCategory> list = umsResourceCategoryService.list();
        return CommonsReturn.success(list);
    }

}
