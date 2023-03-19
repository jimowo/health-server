package com.ymk.health.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "接收前端的用户信息")
public class UserRolesVo implements Serializable {

    private long id;
    private String username;
    private String password;
    private String nickname;
    private Integer sex;
    private String avatar;
    private String address;
    private String wxOpenid;
    private boolean status;
    private boolean admin;
    private String phone;
    private String email;
    private List<Long> roles;

}
