package com.app.dmm.modules.sys.mapper;

import com.app.dmm.core.mapper.MyBaseMapper;
import com.app.dmm.modules.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper extends MyBaseMapper<SysRole> {
    /**
     *通过用户ID查询相应角色
     */

    List<SysRole> findByUserId(@Param("userId")Long userId);
}
