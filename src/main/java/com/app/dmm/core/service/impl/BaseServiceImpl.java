package com.app.dmm.core.service.impl;

import com.app.dmm.core.mapper.MyBaseMapper;
import com.app.dmm.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private MyBaseMapper<T> myBaseMapper;
    @Override
    public T selectByID(Long id) {
        return myBaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List findAll() {
        return myBaseMapper.selectAll();
    }

    @Override
    public int save(T entity) {
        return myBaseMapper.insert(entity);
    }

    @Override
    public int update(T entity) {
        return myBaseMapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateBatch(String hql, Object... values) {
        return 0;
    }

    @Override
    public int delete(T entity) {
        return myBaseMapper.delete(entity);
    }

    @Override
    public int deleteById(Long id) {
        return myBaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByIds(String ids) {
        return myBaseMapper.deleteByIds(ids);
    }
}
