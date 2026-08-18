package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 文件下载信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FileDownloadInfo implements Dto {

    private static final long serialVersionUID = -9179463154136750308L;

    private TaskItemKey key;

    public FileDownloadInfo() {
    }

    public FileDownloadInfo(TaskItemKey key) {
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
        return "FileDownloadInfo{" +
                "key=" + key +
                '}';
    }
}
