package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导入模板信息已经存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportTemplateInfoExistsException extends HandlerException {

    private static final long serialVersionUID = -2377472236060880467L;

    private final TaskSettingItemKey importTemplateInfoKey;

    public ImportTemplateInfoExistsException(TaskSettingItemKey importTemplateInfoKey) {
        this.importTemplateInfoKey = importTemplateInfoKey;
    }

    public ImportTemplateInfoExistsException(Throwable cause, TaskSettingItemKey importTemplateInfoKey) {
        super(cause);
        this.importTemplateInfoKey = importTemplateInfoKey;
    }

    @Override
    public String getMessage() {
        return "导入模板信息 " + importTemplateInfoKey + " 已经存在";
    }
}
