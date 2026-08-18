package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导出元数据不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExportMetadataNotExistsException extends HandlerException {

    private static final long serialVersionUID = -5849553837731035820L;

    private final TaskItemKey exportMetadataKey;

    public ExportMetadataNotExistsException(TaskItemKey exportMetadataKey) {
        this.exportMetadataKey = exportMetadataKey;
    }

    public ExportMetadataNotExistsException(Throwable cause, TaskItemKey exportMetadataKey) {
        super(cause);
        this.exportMetadataKey = exportMetadataKey;
    }

    @Override
    public String getMessage() {
        return "导出元数据 " + exportMetadataKey + " 不存在";
    }
}
