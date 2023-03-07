package com.ymk.health.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "分页数据")
public class PageResult extends Result implements Serializable {

    @ApiModelProperty(value = "总记录数", dataType = "long")
    private long total;

    @ApiModelProperty(value = "分页的数据", dataType = "list")
    private List<?> rows;

    private PageResult(long total, List<?> list) {
        this.setFlag(true);
        this.setMsg("分页查询成功");
        this.total = total;
        this.rows = list;
    }

    /**
     * 分页查询成功
     * @param total 总记录数
     * @param list 查询结果
     * @return Result
     */
    public static PageResult pageSuccess(long total, List<?> list) {
        return new PageResult(total, list);
    }
}
