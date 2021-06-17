package com.app.dmm.core.mapper;

import tk.mybatis.mapper.common.*;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 *  @author 卡其蓝
 *  创建时间：2020年10月27日 下午1:29:03
 *  @Description: TODO(公用Mapper接口)
 *
 */
public interface MyBaseMapper<T> extends BaseMapper<T>, ConditionMapper<T>, IdsMapper<T>, InsertListMapper<T> {
}
