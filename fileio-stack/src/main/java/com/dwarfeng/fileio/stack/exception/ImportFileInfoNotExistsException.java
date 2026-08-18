package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导入文件信息不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportFileInfoNotExistsException extends HandlerException {

    private static final long serialVersionUID = -5319826252712575416L;

    private final TaskItemKey importFileInfoKey;

    public ImportFileInfoNotExistsException(TaskItemKey importFileInfoKey) {
        this.importFileInfoKey = importFileInfoKey;
    }

    public ImportFileInfoNotExistsException(Throwable cause, TaskItemKey importFileInfoKey) {
        super(cause);
        this.importFileInfoKey = importFileInfoKey;
    }

    @Override
    public String getMessage() {
        return "导入文件信息 " + importFileInfoKey + " 不存在";
    }
}
