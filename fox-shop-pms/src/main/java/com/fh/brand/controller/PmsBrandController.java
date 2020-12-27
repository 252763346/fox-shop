package com.fh.brand.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.brand.entity.PmsBrand;
import com.fh.brand.service.IPmsBrandService;
import com.fh.utils.CommonsReturn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author lds
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
    @Resource
    IPmsBrandService pmsBrandService;

    //商品维护中品牌下拉框查询
    @GetMapping("all")
    public CommonsReturn getBrandAll(){
        List<PmsBrand> brandList = pmsBrandService.list();
        return CommonsReturn.success(brandList);
    }

    //回显
    @GetMapping("/huixian")
    //@PreAuthorize("hasAnyAuthority('pms:brand')")
    public CommonsReturn huixian(Integer id){
        PmsBrand pmsBrand = pmsBrandService.getById(id);
        return CommonsReturn.success(pmsBrand);
    }

    //新增/修改
    @PostMapping
    public CommonsReturn saveOrUpdate(PmsBrand pmsBrand){
        pmsBrandService.saveOrUpdate(pmsBrand);
        return CommonsReturn.success();
    }

    //分页查询
    @GetMapping
    public CommonsReturn queryBrand(Page<PmsBrand> page){
        IPage<PmsBrand> iPage=pmsBrandService.page(page);
        return CommonsReturn.success(iPage);
    }

}
