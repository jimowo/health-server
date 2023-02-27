package com.ymk.health.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel(value = "菜单")
public class SysMenu {

    @ApiModelProperty(value = "主键")
    private long id;

    @ApiModelProperty(value = "路由")
    private String path;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单名")
    private String title;

    @ApiModelProperty(value = "前端组件")
    private String component;

    @ApiModelProperty(value = "父菜单id")
    private long parentId;

    @ApiModelProperty(value = "子菜单")
    private List<SysMenu> children;
}
