package com.app.dmm.modules.sys.service.impl;

import com.app.dmm.core.service.impl.BaseServiceImpl;
import com.app.dmm.modules.sys.entity.SysRole;
import com.app.dmm.modules.sys.mapper.SysRoleMapper;
import com.app.dmm.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> findByUserId(Long userId) {
        return sysRoleMapper.findByUserId(userId);
    }
}
