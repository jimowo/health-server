package com.ymk.health.mapper;

import com.github.pagehelper.Page;
import com.ymk.health.entity.Permission;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SysPermissionMapper {

    /**
     * 添加权限信息
     * @param permission
     */
    void insert(Permission permission);

    /**
     * 修改权限信息
     * @param permission
     */
    void update(Permission permission);

    /**
     * 删除权限信息
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查询权限信息
     * @param queryString
     * @return
     */
    Page<Permission> findPage(String queryString);
}
