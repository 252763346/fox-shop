package com.fh.category.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.category.categorySearch;
import com.fh.category.entity.PmsProductCategory;
import com.fh.category.service.IPmsProductCategoryService;
import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/category")
public class PmsProductCategoryController {
    @Resource
    IPmsProductCategoryService categoryService;

    //递归查询商品分类全部信息 商品维护中商品分类下拉框查询
    @GetMapping("all")
    public CommonsReturn queryCategoryAll(){
        List<Map<String,Object>> list=categoryService.queryCategoryAll();
        return CommonsReturn.success(list);
    }

    //新增
    @PostMapping
    public CommonsReturn saveCategory(PmsProductCategory pmsProductCategory){
        Long parentId=pmsProductCategory.getParentId();
        //一级节点
        if(parentId == -1){
            pmsProductCategory.setLevel(0);
            pmsProductCategory.setParentId(0l);
        }else{//二级节点
            pmsProductCategory.setLevel(1);
        }
        categoryService.save(pmsProductCategory);
        return CommonsReturn.success();
    }

    //动态下拉框查询
    @GetMapping("/parent")
    public CommonsReturn queryParentCategoryList(){
        //只查询第一级分类 level为0
        QueryWrapper<PmsProductCategory> queryWrapper=new QueryWrapper<PmsProductCategory>();
        queryWrapper.eq("level",0);
        List<PmsProductCategory> parentList = categoryService.list(queryWrapper);
        return CommonsReturn.success(parentList);
    }

    //分页查询
    @GetMapping
    public CommonsReturn queryCategory(Page<PmsProductCategory> page, categorySearch categorySearch){
        QueryWrapper<PmsProductCategory> queryWrapper=new QueryWrapper<PmsProductCategory>();
        //当前为二级节点
        if(categorySearch.getPid()!=null){
            queryWrapper.eq("parent_Id",categorySearch.getPid());
        }else{//只查看一级节点
            queryWrapper.eq("level",categorySearch.getLevel());
        }
        IPage<PmsProductCategory> iPage=categoryService.page(page,queryWrapper);
        return CommonsReturn.success(iPage);
    }

}
