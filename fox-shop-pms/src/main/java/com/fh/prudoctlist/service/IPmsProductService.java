package com.fh.prudoctlist.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fh.prudoctlist.entity.PmsProduct;
import com.fh.prudoctlist.entity.PmsProductBO;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author lds
 * @since 2020-12-13
 */
public interface IPmsProductService extends IService<PmsProduct> {

    void createProduct(PmsProductBO pmsProductBO);

    PmsProductBO getProductById(Long id);
}
