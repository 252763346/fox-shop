package com.fh.prudoctlist.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.brand.entity.PmsBrand;
import com.fh.brand.service.IPmsBrandService;
import com.fh.category.entity.PmsProductCategory;
import com.fh.category.service.IPmsProductCategoryService;
import com.fh.prudoctlist.entity.PmsProduct;
import com.fh.prudoctlist.entity.PmsProductBO;
import com.fh.prudoctlist.service.IPmsProductService;
import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-13
 */
@RestController
@RequestMapping("/product")
public class PmsProductController {
    @Resource
    IPmsProductService pmsProductService;
    @Resource
    IPmsBrandService pmsBrandService;
    @Resource
    IPmsProductCategoryService pmsProductCategoryService;

    //修改页面回显数据
    @GetMapping("/{id}")
    public CommonsReturn getProductById(@PathVariable("id") Long id){
        PmsProductBO productBO=pmsProductService.getProductById(id);
        return CommonsReturn.success(productBO);
    }


    //商品维护进行提交
    @PostMapping
    public CommonsReturn saveOrUpdateProductData(@RequestBody PmsProductBO pmsProductBO){

        pmsProductService.createProduct(pmsProductBO);

       /* if(pmsProduct.getBrandId()!=null){
            PmsBrand brand = pmsBrandService.getById(pmsProduct.getBrandId());
            pmsProduct.setBrandName(brand.getName());
        }
        if(pmsProduct.getProductCategoryId()!=null){
            PmsProductCategory category = pmsProductCategoryService.getById(pmsProduct.getProductCategoryId());
            pmsProduct.setProductCategoryName(category.getName());
        }
        pmsProductService.saveOrUpdate(pmsProduct);*/

        return CommonsReturn.success();
    }

    //分页查询
    @GetMapping
    public CommonsReturn queryProductrListData(Page<PmsProduct> page){
        QueryWrapper<PmsProduct> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<PmsProduct> ipage=pmsProductService.page(page,queryWrapper);
        return CommonsReturn.success(ipage);
    }

}
