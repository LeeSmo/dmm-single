package com.app.dmm.modules.user.service;

import com.app.dmm.core.service.BaseService;
import com.app.dmm.modules.user.entity.SysUser;

import java.util.List;

public interface SysUserService extends BaseService<SysUser> {
    /**
     * 通过username查询用户
     */
    SysUser findByUserName(String username);

    /**
     * 分页查询用户
     * @return
     */
    List<SysUser> selectUserPageList();
}
