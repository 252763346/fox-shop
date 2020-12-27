package com.fh.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.admin.entity.UmsAdmin;
import com.fh.resource.entity.UmsResource;
import com.fh.role.entity.UmsRole;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author lds
 * @since 2020-12-07
 */
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {

    List<UmsResource> queryResourceByAdmin(Long id);

    List<UmsRole> queryRoleByAdmin(Long id);
}
