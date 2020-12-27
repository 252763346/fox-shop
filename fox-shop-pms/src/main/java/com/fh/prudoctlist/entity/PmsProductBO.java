package com.fh.prudoctlist.entity;

import lombok.Data;

import java.util.List;


@Data
public class PmsProductBO extends PmsProduct {

        //会员价格集合
        private List<PmsMemberPrice> memberPriceList;
        //阶梯价格维护
        private  List<PmsProductLadder> productLadderList;
        //满减价格维护
        private List<PmsProductFullReduction> productFullReductionList;
        //sku维护
        private List<PmsSkuStock> skuStockList;
        //商品属性和参数列表
        private  List<PmsProductAttributeValue> productAttributeValueList;
}
