package com.fh.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.menu.entity.UmsMenu;

import java.util.List;

/**
 * <p>
 * 后台菜单表 Mapper 接口
 * </p>
 *
 * @author lds
 * @since 2020-12-17
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    List<UmsMenu> queryMenuTreeList(String username);
}
