package com.app.dmm.core.service;


import java.io.Serializable;
import java.util.List;

/**
 *  基础Service实现类
 */
public interface BaseService<T>  {
    /**
     * 查找单个对象
     * 通过get方法查找对象; 适用于需要直接获取对象的属性值
     */
    public T selectByID(Long id);
    /**
     * 查找所有对象
     */
    public List<T> findAll();
    /**
     * 保存
     */
    public int save(T entity);

    /**
     * 修改
     */
    public int update(T entity);
    /**
     * 批量修改
     */
    public int updateBatch(String hql, Object... values);

    /**
     * 删除实体
     */
    public int delete(T entity);
    /**
     * 通过主键删除实体
     */
    public int deleteById(Long id);

    /**
     * 通过主键数组批量删除实体
     */
    public int deleteByIds(String ids);
   // public List<T> findPageList(PageUtil page) {
}
