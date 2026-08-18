package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 元数据删除信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class MetadataRemoveInfo implements Dto {

    private static final long serialVersionUID = -7507072953133780647L;

    private TaskItemKey key;

    public MetadataRemoveInfo() {
    }

    public MetadataRemoveInfo(TaskItemKey key) {
        this.key = key;
    }

    public TaskItemKey getKey() {
        return key;
    }

    public void setKey(TaskItemKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "MetadataRemoveInfo{" +
                "key=" + key +
                '}';
    }
}
