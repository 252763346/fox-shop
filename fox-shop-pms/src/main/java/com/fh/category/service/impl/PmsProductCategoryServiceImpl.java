package com.fh.category.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fh.category.entity.PmsProductCategory;
import com.fh.category.mapper.PmsProductCategoryMapper;
import com.fh.category.service.IPmsProductCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author lds
 * @since 2020-12-09
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements IPmsProductCategoryService {

    //递归查询商品分类
    @Override
    public List<Map<String, Object>> queryCategoryAll() {
        List<PmsProductCategory> categoryList=super.list();
        return getCategoryChildren(categoryList,0l);
    }
    private List<Map<String,Object>> getCategoryChildren(List<PmsProductCategory> categoryList, Long pid) {
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        categoryList.forEach(category -> {
            Map<String,Object> map=null;
            if(category.getParentId().equals(pid)){
                map=new HashMap<String,Object>();
                map.put("value",category.getId());
                map.put("label",category.getName());
                if(category.getLevel() == 0){
                    map.put("children",getCategoryChildren(categoryList,category.getId()));
                }
            }
            if(map != null){
                list.add(map);
            }
        });
        return list;
    }


}
