package com.app.dmm.modules.user.service.impl;

import com.app.dmm.core.service.impl.BaseServiceImpl;
import com.app.dmm.modules.user.entity.SysUser;
import com.app.dmm.modules.user.mapper.SysUserMapper;
import com.app.dmm.modules.user.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUserName(String username) {
        return sysUserMapper.findByUserName(username);
    }

    @Override
    public List<SysUser> selectUserPageList() {
        return null;
    }

}
