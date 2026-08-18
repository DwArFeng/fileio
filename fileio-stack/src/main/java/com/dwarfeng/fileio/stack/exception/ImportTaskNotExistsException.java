package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导入任务不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportTaskNotExistsException extends HandlerException {

    private static final long serialVersionUID = -9163772669911168735L;

    private final LongIdKey importTaskKey;

    public ImportTaskNotExistsException(LongIdKey importTaskKey) {
        this.importTaskKey = importTaskKey;
    }

    public ImportTaskNotExistsException(Throwable cause, LongIdKey importTaskKey) {
        super(cause);
        this.importTaskKey = importTaskKey;
    }

    @Override
    public String getMessage() {
        return "导入任务 " + importTaskKey + " 不存在";
    }
}
