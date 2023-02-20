package com.ymk.health.mapper;

import com.ymk.health.entity.Permission;
import com.ymk.health.entity.SysMenu;
import com.ymk.health.entity.SysRole;
import com.ymk.health.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {

    List<SysUser> findAll();

    SysUser findUserByUsername(@Param("username") String Username);

    List<SysRole> findRoles(@Param("userId") Long userId);

    List<SysMenu> findMenus(@Param("userId") Long userId);

    List<SysMenu> findChildMenus(@Param("userID") Long userId, @Param("parentId") Long parentId);

    List<Permission> findPermissions(@Param("userId") Long userId);
}
