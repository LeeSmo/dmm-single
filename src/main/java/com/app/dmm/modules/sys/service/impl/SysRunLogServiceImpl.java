package com.app.dmm.modules.sys.service.impl;

import com.app.dmm.core.service.impl.BaseServiceImpl;
import com.app.dmm.modules.sys.entity.SysRunLog;
import com.app.dmm.modules.sys.mapper.SysRoleMapper;
import com.app.dmm.modules.sys.service.SysRunLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRunLogServiceImpl extends BaseServiceImpl<SysRunLog> implements SysRunLogService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
}
