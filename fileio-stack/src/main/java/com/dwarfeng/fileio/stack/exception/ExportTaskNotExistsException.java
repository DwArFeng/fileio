package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导出任务不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExportTaskNotExistsException extends HandlerException {

    private static final long serialVersionUID = -5433670494787497209L;

    private final LongIdKey exportTaskKey;

    public ExportTaskNotExistsException(LongIdKey exportTaskKey) {
        this.exportTaskKey = exportTaskKey;
    }

    public ExportTaskNotExistsException(Throwable cause, LongIdKey exportTaskKey) {
        super(cause);
        this.exportTaskKey = exportTaskKey;
    }

    @Override
    public String getMessage() {
        return "导出任务 " + exportTaskKey + " 不存在";
    }
}
