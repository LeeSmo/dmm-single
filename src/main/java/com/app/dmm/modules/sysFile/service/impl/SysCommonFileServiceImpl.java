package com.app.dmm.modules.sysFile.service.impl;

import com.app.dmm.core.service.impl.BaseServiceImpl;
import com.app.dmm.modules.sysFile.entity.SysCommonFile;
import com.app.dmm.modules.sysFile.mapper.SysCommonFileMapper;
import com.app.dmm.modules.sysFile.service.SysCommonFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysCommonFileServiceImpl extends BaseServiceImpl<SysCommonFile> implements SysCommonFileService {
    @Autowired
    private SysCommonFileMapper sysCommonFileMapper;
}
