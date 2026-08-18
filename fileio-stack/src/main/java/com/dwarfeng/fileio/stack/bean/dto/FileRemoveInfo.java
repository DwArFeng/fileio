package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 文件移除信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FileRemoveInfo implements Dto {

    private static final long serialVersionUID = 3110098928818730261L;

    private TaskItemKey key;

    public FileRemoveInfo() {
    }

    public FileRemoveInfo(TaskItemKey key) {
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
        return "FileRemoveInfo{" +
                "key=" + key +
                '}';
    }
}
