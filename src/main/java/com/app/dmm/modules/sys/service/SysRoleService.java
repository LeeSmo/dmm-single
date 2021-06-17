package com.app.dmm.modules.sys.service;

import com.app.dmm.core.service.BaseService;
import com.app.dmm.modules.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleService extends BaseService<SysRole> {
    /**
     *通过用户ID查询相应角色
     */

    List<SysRole> findByUserId(@Param("userId")Long userId);
}
