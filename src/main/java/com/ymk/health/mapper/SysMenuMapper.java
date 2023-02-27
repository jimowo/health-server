package com.ymk.health.mapper;

import com.github.pagehelper.Page;
import com.ymk.health.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysMenuMapper {

    /**
     * 添加菜单信息
     * @param menu
     */
    void insert(SysMenu menu);

    /**
     * 修改权限信息
     * @param menu
     */
    void update(SysMenu menu);

    /**
     * 删除菜单信息
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查询权限信息
     * @param queryString
     * @return
     */
    Page<SysMenu> findPage(String queryString);
}
