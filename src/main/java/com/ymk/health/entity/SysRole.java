package com.ymk.health.entity;

import lombok.Data;

import java.util.List;

/**
 * 角色信息实体
 */
@Data
public class SysRole {

    private long id;
    private String label;
    private String code;
    /**
     * 角色拥有的权限
     */
    private List<Permission> permissions;

    /**
     * 角色对应的菜单列表
     */
    private List<SysMenu> menus;
}
