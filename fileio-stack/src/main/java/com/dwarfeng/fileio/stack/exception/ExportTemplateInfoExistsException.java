package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导出模板信息已经存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExportTemplateInfoExistsException extends HandlerException {

    private static final long serialVersionUID = -4708585348418709427L;

    private final TaskSettingItemKey exportTemplateInfoKey;

    public ExportTemplateInfoExistsException(TaskSettingItemKey exportTemplateInfoKey) {
        this.exportTemplateInfoKey = exportTemplateInfoKey;
    }

    public ExportTemplateInfoExistsException(Throwable cause, TaskSettingItemKey exportTemplateInfoKey) {
        super(cause);
        this.exportTemplateInfoKey = exportTemplateInfoKey;
    }

    @Override
    public String getMessage() {
        return "导出模板信息 " + exportTemplateInfoKey + " 已经存在";
    }
}
