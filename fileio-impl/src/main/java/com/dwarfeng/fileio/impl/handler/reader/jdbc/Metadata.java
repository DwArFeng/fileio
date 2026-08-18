package com.dwarfeng.fileio.impl.handler.reader.jdbc;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.List;

/**
 * 元数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Metadata implements Bean {

    private static final long serialVersionUID = 1617404269980302026L;

    @JSONField(name = "#query_params", ordinal = 1, deserialize = false)
    private String queryParamsRem = "查询参数列表，用于指定查询语句中的参数。" +
            "查询参数列表的长度应该与 sql 中的 ? 数量一致。" +
            "查询参数列表中的每一个值应该是基于 DCT 框架编码后的值。";

    @JSONField(name = "query_params", ordinal = 2)
    private List<String> queryParams;

    public Metadata() {
    }

    public Metadata(List<String> queryParams) {
        this.queryParams = queryParams;
    }

    public String getQueryParamsRem() {
        return queryParamsRem;
    }

    public void setQueryParamsRem(String queryParamsRem) {
        this.queryParamsRem = queryParamsRem;
    }

    public List<String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<String> queryParams) {
        this.queryParams = queryParams;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "queryParamsRem='" + queryParamsRem + '\'' +
                ", queryParams=" + queryParams +
                '}';
    }
}
