package com.app.dmm.modules.user.mapper;

import com.app.dmm.core.mapper.MyBaseMapper;
import com.app.dmm.modules.user.entity.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends MyBaseMapper<SysUser> {
    /**
     * 通过loginId查询用户
     */
    SysUser findByUserName(String username);
}
