package com.ymk.health.service;

import com.ymk.health.entity.SysMenu;
import com.ymk.health.utils.QueryInfo;
import com.ymk.health.utils.Result;

public interface SysMenuService {

    /**
     * 分页查询
     * @param queryInfo 页数 页码 查询内容
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加菜单数据
     * @param menu
     * @return
     */
    Result insert(SysMenu menu);

    /**
     * 删除菜单数据
     * @param id
     * @return
     */
    Result delete(long id);

    /**
     * 修改菜单数据
     * @param menu
     * @return
     */
    Result update(SysMenu menu);
}
