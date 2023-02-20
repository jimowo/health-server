package com.ymk.health.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageResult<T> extends Result implements Serializable {

    /**
     * 总记录数
     */
    private long total;

    /**
     * 分页的数据
     */
    private List<T> rows;

    public PageResult(long total, List<T> list) {
        this.setFlag(true);
        this.setMsg("分页查询成功");
        this.total = total;
        this.rows = list;
    }
}
