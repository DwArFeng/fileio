package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导入任务设置不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportTaskSettingNotExistsException extends HandlerException {

    private static final long serialVersionUID = -4485894762748583876L;

    private final LongIdKey importTaskSettingKey;

    public ImportTaskSettingNotExistsException(LongIdKey importTaskSettingKey) {
        this.importTaskSettingKey = importTaskSettingKey;
    }

    public ImportTaskSettingNotExistsException(Throwable cause, LongIdKey importTaskSettingKey) {
        super(cause);
        this.importTaskSettingKey = importTaskSettingKey;
    }

    @Override
    public String getMessage() {
        return "导入任务设置 " + importTaskSettingKey + " 不存在";
    }
}
