package com.fh.brand.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fh.brand.entity.PmsBrand;
import com.fh.brand.mapper.PmsBrandMapper;
import com.fh.brand.service.IPmsBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author lds
 * @since 2020-12-08
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements IPmsBrandService {
    @Resource
    PmsBrandMapper brandMapper;

}
