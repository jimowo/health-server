package com.ymk.health.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "登录参数")
public class LoginVo {

    @ApiModelProperty(value = "用户名", dataType = "String")
    private String username;

    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;
}
