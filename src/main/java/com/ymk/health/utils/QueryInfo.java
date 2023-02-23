package com.ymk.health.utils;

import lombok.Data;

/**
 * 分页的每页的信息
 */
@Data
public class QueryInfo {

    /**
     * 第几页
     */
    private Integer pageNumber;

    /**
     * 一页多少数据
     */
    private Integer pageSize;

    /**
     * 查询的内容
     */
    private String queryString;
}
