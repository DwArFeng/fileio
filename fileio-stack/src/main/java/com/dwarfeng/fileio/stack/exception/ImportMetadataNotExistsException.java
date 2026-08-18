package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导入元数据不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportMetadataNotExistsException extends HandlerException {

    private static final long serialVersionUID = 4366743910767334416L;

    private final TaskItemKey importMetadataKey;

    public ImportMetadataNotExistsException(TaskItemKey importMetadataKey) {
        this.importMetadataKey = importMetadataKey;
    }

    public ImportMetadataNotExistsException(Throwable cause, TaskItemKey importMetadataKey) {
        super(cause);
        this.importMetadataKey = importMetadataKey;
    }

    @Override
    public String getMessage() {
        return "导入元数据 " + importMetadataKey + " 不存在";
    }
}
