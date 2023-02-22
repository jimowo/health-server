package com.ymk.health.entity;

import lombok.Data;

import java.util.List;

/**
 * 菜单
 */
@Data
public class SysMenu {

    private long id;
    /**
     * 路由
     */
    private String path;
    private String icon;
    private String title;
    private String component;
    private long parentId;
    /**
     * 子菜单
     */
    private List<SysMenu> children;
}
