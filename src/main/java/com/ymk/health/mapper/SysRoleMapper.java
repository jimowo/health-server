package com.ymk.health.mapper;

import com.github.pagehelper.Page;
import com.ymk.health.entity.SysMenu;
import com.ymk.health.entity.SysRole;
import com.ymk.health.vo.RoleMenusPermissionsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    /**
     * 添加角色信息
     */
    void insert(SysRole role);

    void insertByParam(RoleMenusPermissionsVo role);

    /**
     * 修改角色信息
     */
    void update(SysRole role);

    void updateByParam(RoleMenusPermissionsVo role);

    /**
     * 删除角色信息
     */
    void delete(Long id);

    /**
     * 分页查询角色信息
     */
    Page<SysRole> findPage(String queryString);

    /**
     * 根据code 查询角色 id
     */
    int findRoleId(String code);

    /**
     * 根据角色查询子菜单信息
     */
    List<SysMenu> findChildMenus(@Param("roleId") Long roleId, @Param("parentId") Long parentId);

    /**
     * 绑定角色和菜单信息
     */
    void bindRoleMenus(@Param("roleId") long roleId, @Param("menuId") long menuId);

    /**
     * 绑定角色和权限信息
     */
    void bindRolePermissions(@Param("roleId") long roleId, @Param("permissionId") long permissionId);

    /**
     * 删除角色菜单关联
     */
    void deleteRoleMenus(long roleId);

    /**
     * 删除角色权限关联
     */
    void deleteRolePermissions(long roleId);
}
