package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导出任务设置不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExportTaskSettingNotExistsException extends HandlerException {

    private static final long serialVersionUID = 4794293494886513892L;

    private final LongIdKey exportTaskSettingKey;

    public ExportTaskSettingNotExistsException(LongIdKey exportTaskSettingKey) {
        this.exportTaskSettingKey = exportTaskSettingKey;
    }

    public ExportTaskSettingNotExistsException(Throwable cause, LongIdKey exportTaskSettingKey) {
        super(cause);
        this.exportTaskSettingKey = exportTaskSettingKey;
    }

    @Override
    public String getMessage() {
        return "导出任务设置 " + exportTaskSettingKey + " 不存在";
    }
}
