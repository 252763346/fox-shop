package com.fh.prudoctlist.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fh.prudoctlist.entity.*;
import com.fh.prudoctlist.mapper.PmsProductMapper;
import com.fh.prudoctlist.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author lds
 * @since 2020-12-13
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements IPmsProductService {
    @Resource
    IPmsProductService pmsProductService;
    @Resource
    IPmsMemberPriceService pmsMemberPriceService;
    @Resource
    IPmsProductLadderService pmsProductLadderService;
    @Resource
    IPmsProductFullReductionService pmsProductFullReductionService;
    @Resource
    IPmsSkuStockService pmsSkuStockService;
    @Resource
    IPmsProductAttributeValueService pmsProductAttributeValueService;


    @Override
    @Transactional  //该注解是对事物的控制  只读，回滚
    public void createProduct(PmsProductBO pmsProductBO) {
        PmsProduct Product=pmsProductBO;
        boolean status = pmsProductService.saveOrUpdate(Product);
        if(status){
            //新增成功后自动获取主键id
            Long productId=Product.getId();
            //保存会员价格
            saveMemberPrice(pmsProductBO.getMemberPriceList(),productId);
            //阶梯价格维护
            saveProductLadder(pmsProductBO.getProductLadderList(),productId);
            //满减价格维护
            saveProductFullReduction(pmsProductBO.getProductFullReductionList(),productId);
            //sku维护
            saveSkuStock(pmsProductBO.getSkuStockList(),productId);
            //商品属性和参数列表
            saveProductAttributeValue(pmsProductBO.getProductAttributeValueList(),productId);
        }
    }

    //修改页面回显数据
    @Override
    public PmsProductBO getProductById(Long id) {
        //查询商品信息
        PmsProductBO productBO=new PmsProductBO();
        BeanUtils.copyProperties(pmsProductService.getById(id),productBO);
        Map<String,Object> map=new HashMap<>();
        map.put("product_id",id);
        //保存会员价格
        List<PmsMemberPrice> pmsMemberPrices = pmsMemberPriceService.listByMap(map);
        //阶梯价格维护
        List<PmsProductLadder> pmsProductLadders = pmsProductLadderService.listByMap(map);
        //满减价格维护
        List<PmsProductFullReduction> pmsProductFullReductions = pmsProductFullReductionService.listByMap(map);
        //sku维护
        List<PmsSkuStock> pmsSkuStocks = pmsSkuStockService.listByMap(map);
        //商品属性和参数列表
        List<PmsProductAttributeValue> pmsProductAttributeValues = pmsProductAttributeValueService.listByMap(map);

        productBO.setMemberPriceList(pmsMemberPrices);
        productBO.setProductLadderList(pmsProductLadders);
        productBO.setProductFullReductionList(pmsProductFullReductions);
        productBO.setSkuStockList(pmsSkuStocks);
        productBO.setProductAttributeValueList(pmsProductAttributeValues);

        return productBO;
    }

    private void saveProductAttributeValue(List<PmsProductAttributeValue> productAttributeValueList, Long productId) {
        productAttributeValueList.forEach(productAttributeValue->{
            productAttributeValue.setProductId(productId);
        });
        pmsProductAttributeValueService.remove(new QueryWrapper<PmsProductAttributeValue>().eq("product_id",productId));
        pmsProductAttributeValueService.saveBatch(productAttributeValueList);
    }

    private void saveSkuStock(List<PmsSkuStock> skuStockList, Long productId) {
        //设置SKU编码值  前8位年月日 中间6位商品id 后3位SKU的id
        for(int i=0;i<skuStockList.size();i++){
            PmsSkuStock pmsSkuStock = skuStockList.get(i);
            pmsSkuStock.setProductId(productId);
            String date = DateFormatUtils.format(new Date(), "yyyyMMdd");
            String productCode = String.format("%06d", productId);
            String skuCode = String.format("%03d", i + 1);
            pmsSkuStock.setSkuCode(StringUtils.join(date,productCode,skuCode));
        }
        pmsSkuStockService.remove(new QueryWrapper<PmsSkuStock>().eq("product_id",productId));
        pmsSkuStockService.saveBatch(skuStockList);
    }

    private void saveProductFullReduction(List<PmsProductFullReduction> productFullReductionList, Long productId) {
        productFullReductionList.forEach(productFullReduction->{
            productFullReduction.setProductId(productId);
        });
        pmsProductFullReductionService.remove(new QueryWrapper<PmsProductFullReduction>().eq("product_id",productId));
        pmsProductFullReductionService.saveBatch(productFullReductionList);
    }

    private void saveProductLadder(List<PmsProductLadder> productLadderList, Long productId) {
        productLadderList.forEach(productLadder->{
            productLadder.setProductId(productId);
        });
        pmsProductLadderService.remove(new QueryWrapper<PmsProductLadder>().eq("product_id",productId));
        pmsProductLadderService.saveBatch(productLadderList);
    }

    private void saveMemberPrice(List<PmsMemberPrice> list,Long productId){
        list.forEach(memberPrice->{
            memberPrice.setProductId(productId);
        });
        pmsMemberPriceService.remove(new QueryWrapper<PmsMemberPrice>().eq("product_id",productId));
        pmsMemberPriceService.saveBatch(list);
    }


}
