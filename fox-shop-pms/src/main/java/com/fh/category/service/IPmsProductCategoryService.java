package com.fh.category.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fh.category.entity.PmsProductCategory;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author lds
 * @since 2020-12-09
 */
public interface IPmsProductCategoryService extends IService<PmsProductCategory> {

    List<Map<String, Object>> queryCategoryAll();
}
