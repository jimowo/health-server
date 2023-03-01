package com.ymk.health.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "前端发送的绑定角色与菜单权限的参数")
public class RoleMenusPermissionsVo {

    @ApiModelProperty(value = "角色ID")
    private long id;

    @ApiModelProperty(value = "角色标签")
    private String label;

    @ApiModelProperty(value = "角色码")
    private String code;

    @ApiModelProperty(value = "菜单ID列表")
    private List<Long> menus;

    @ApiModelProperty(value = "权限ID列表")
    private List<Long> permissions;
}
