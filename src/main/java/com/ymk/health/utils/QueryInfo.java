package com.ymk.health.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分页信息")
public class QueryInfo {

    @ApiModelProperty("第几页")
    private Integer pageNumber;

    @ApiModelProperty("一页多少数据")
    private Integer pageSize;

    @ApiModelProperty("查询的内容")
    private String queryString;
}
