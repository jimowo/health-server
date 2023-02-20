package com.ymk.health.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 回复给前端的统一回复结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "回复前端统一结果")
public class Result implements Serializable {

    @ApiModelProperty(value = "响应成功标识", dataType = "boolean")
    private boolean flag;

    @ApiModelProperty(value = "响应信息", dataType = "String")
    private String msg;

    @ApiModelProperty(value = "响应数据", dataType = "Object")
    private Object data;

    private Result(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public static Result success(String msg) {
        return new Result(true, msg);
    }

    public static Result success(String msg, Object data) {
        return new Result(true, msg, data);
    }

    public static Result fail(String msg) {
        return new Result(false, msg);
    }
}
