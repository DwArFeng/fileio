package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导出文件信息已经存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExportFileInfoExistsException extends HandlerException {

    private static final long serialVersionUID = 3787559132474651013L;

    private final TaskItemKey exportFileInfoKey;

    public ExportFileInfoExistsException(TaskItemKey exportFileInfoKey) {
        this.exportFileInfoKey = exportFileInfoKey;
    }

    public ExportFileInfoExistsException(Throwable cause, TaskItemKey exportFileInfoKey) {
        super(cause);
        this.exportFileInfoKey = exportFileInfoKey;
    }

    @Override
    public String getMessage() {
        return "导出文件信息 " + exportFileInfoKey + " 已经存在";
    }
}
