package com.ymk.health.mapper;

import com.github.pagehelper.Page;
import com.ymk.health.entity.Permission;
import com.ymk.health.entity.SysMenu;
import com.ymk.health.entity.SysRole;
import com.ymk.health.entity.SysUser;
import com.ymk.health.vo.UserRolesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {

    /**
     * 查询所有用户
     */
    List<SysUser> findAll();

    /**
     * 通过用户名查询用户
     */
    SysUser findUserByUsername(@Param("username") String Username);

    /**
     * 查找用户对应的角色信息
     */
    List<SysRole> findRoles(@Param("userId") Long userId);

    /**
     * 查询用户对应的菜单信息
     */
    List<SysMenu> findMenus(@Param("userId") Long userId);

    /**
     * 查询用户对应的子菜单信息
     */
    List<SysMenu> findChildMenus(@Param("userId") Long userId, @Param("parentId") Long parentId);

    /**
     * 查询用户对应的权限信息
     */
    List<Permission> findPermissions(@Param("userId") Long userId);

    /**
     * 添加用户信息
     */
    void insert(UserRolesVo user);

    /**
     * 修改用户信息
     */
    void update(UserRolesVo user);

    /**
     * 删除用户信息
     */
    void delete(Long id);

    /**
     * 分页查询用户信息
     */
    Page<SysUser> findPage(String queryString);

    /**
     * 绑定用户与角色信息
     */
    void bindUserRoles(@Param("userId") long userId, @Param("roleId") long roleId);

    /**
     * 删除该用户绑定的角色
     */
    void deleteUserRoles(long userId);

    /**
     * 根据用户名查询用户id
     */
    long findUserId(String username);
}
