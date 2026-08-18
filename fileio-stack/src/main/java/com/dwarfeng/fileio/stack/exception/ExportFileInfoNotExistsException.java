package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导出文件信息不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExportFileInfoNotExistsException extends HandlerException {

    private static final long serialVersionUID = 7487123178868760969L;

    private final TaskItemKey exportFileInfoKey;

    public ExportFileInfoNotExistsException(TaskItemKey exportFileInfoKey) {
        this.exportFileInfoKey = exportFileInfoKey;
    }

    public ExportFileInfoNotExistsException(Throwable cause, TaskItemKey exportFileInfoKey) {
        super(cause);
        this.exportFileInfoKey = exportFileInfoKey;
    }

    @Override
    public String getMessage() {
        return "导出文件信息 " + exportFileInfoKey + " 不存在";
    }
}
