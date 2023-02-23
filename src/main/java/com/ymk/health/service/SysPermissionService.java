package com.ymk.health.service;

import com.ymk.health.entity.Permission;
import com.ymk.health.utils.QueryInfo;
import com.ymk.health.utils.Result;

public interface SysPermissionService {

    /**
     * 分页查询
     * @param queryInfo 页数 页码 查询内容
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加权限数据
     * @param permission
     * @return
     */
    Result insert(Permission permission);

    /**
     * 删除权限数据
     * @param id
     * @return
     */
    Result delete(long id);

    /**
     * 修改权限数据
     * @param permission
     * @return
     */
    Result update(Permission permission);
}
