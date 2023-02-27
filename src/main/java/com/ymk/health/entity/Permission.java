package com.ymk.health.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "权限信息")
public class Permission {

    @ApiModelProperty(value = "主键")
    private long id;

    @ApiModelProperty(value = "权限标签")
    private String label;

    @ApiModelProperty(value = "权限码")
    private String code;
}
