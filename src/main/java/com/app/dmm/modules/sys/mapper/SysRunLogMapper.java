package com.app.dmm.modules.sys.mapper;

import com.app.dmm.core.mapper.MyBaseMapper;
import com.app.dmm.modules.sys.entity.SysRunLog;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRunLogMapper extends MyBaseMapper<SysRunLog> {
    /**
     *通过用户ID查询相应角色
     */
}
