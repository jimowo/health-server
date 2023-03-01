package com.ymk.health.service;

import com.ymk.health.utils.QueryInfo;
import com.ymk.health.utils.Result;
import com.ymk.health.vo.RoleMenusPermissionsVo;

public interface SysRoleService {

    /**
     * 分页查询
     * @param queryInfo 页数 页码 查询内容
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加角色数据
     * @param role
     * @return
     */
    Result insert(RoleMenusPermissionsVo role);

    /**
     * 删除角色数据
     * @param id
     * @return
     */
    Result delete(long id);

    /**
     * 修改角色数据
     * @param role
     * @return
     */
    Result update(RoleMenusPermissionsVo role);
}
