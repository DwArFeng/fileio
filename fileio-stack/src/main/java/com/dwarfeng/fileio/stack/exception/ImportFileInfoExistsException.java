package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导出文件信息已经存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportFileInfoExistsException extends HandlerException {

    private static final long serialVersionUID = 1517230715989246501L;

    private final TaskItemKey importFileInfoKey;

    public ImportFileInfoExistsException(TaskItemKey importFileInfoKey) {
        this.importFileInfoKey = importFileInfoKey;
    }

    public ImportFileInfoExistsException(Throwable cause, TaskItemKey importFileInfoKey) {
        super(cause);
        this.importFileInfoKey = importFileInfoKey;
    }

    @Override
    public String getMessage() {
        return "导出文件信息 " + importFileInfoKey + " 已经存在";
    }
}
