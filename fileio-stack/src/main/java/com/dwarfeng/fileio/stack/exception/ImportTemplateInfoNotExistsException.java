package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导入模板信息不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportTemplateInfoNotExistsException extends HandlerException {

    private static final long serialVersionUID = 8748241238482150042L;

    private final TaskSettingItemKey importTemplateInfoKey;

    public ImportTemplateInfoNotExistsException(TaskSettingItemKey importTemplateInfoKey) {
        this.importTemplateInfoKey = importTemplateInfoKey;
    }

    public ImportTemplateInfoNotExistsException(Throwable cause, TaskSettingItemKey importTemplateInfoKey) {
        super(cause);
        this.importTemplateInfoKey = importTemplateInfoKey;
    }

    @Override
    public String getMessage() {
        return "导入模板信息 " + importTemplateInfoKey + " 不存在";
    }
}
