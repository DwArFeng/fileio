package com.dwarfeng.fileio.impl.handler.reader.mock;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 元数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Metadata implements Dto {

    private static final long serialVersionUID = 5931225504311852590L;

    @JSONField(name = "total_size", ordinal = 1)
    private int totalSize;

    public Metadata() {
    }

    public Metadata(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "totalSize=" + totalSize +
                '}';
    }
}
