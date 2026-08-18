package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导出模板信息不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExportTemplateInfoNotExistsException extends HandlerException {

    private static final long serialVersionUID = 371500678481485300L;

    private final TaskSettingItemKey exportTemplateInfoKey;

    public ExportTemplateInfoNotExistsException(TaskSettingItemKey exportTemplateInfoKey) {
        this.exportTemplateInfoKey = exportTemplateInfoKey;
    }

    public ExportTemplateInfoNotExistsException(Throwable cause, TaskSettingItemKey exportTemplateInfoKey) {
        super(cause);
        this.exportTemplateInfoKey = exportTemplateInfoKey;
    }

    @Override
    public String getMessage() {
        return "导出模板信息 " + exportTemplateInfoKey + " 不存在";
    }
}
