package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.fileio.stack.handler.TemporaryFileHandler.TemporaryFileType;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Optional;

/**
 * 临时文件不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemporaryFileNotExistsException extends TemporaryFileException {

    private static final long serialVersionUID = 6277164080506062641L;

    private final TemporaryFileType type;
    private final LongIdKey taskKey;
    private final String name;

    public TemporaryFileNotExistsException(TemporaryFileType type, LongIdKey taskKey, String name) {
        this.type = type;
        this.taskKey = taskKey;
        this.name = name;
    }

    public TemporaryFileNotExistsException(Throwable cause, TemporaryFileType type, LongIdKey taskKey, String name) {
        super(cause);
        this.type = type;
        this.taskKey = taskKey;
        this.name = name;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public String getMessage() {
        String typeString;
        switch (type) {
            case EXPORT_TASK_TEMPORARY_FILE:
                typeString = "导出任务临时文件";
                break;
            case IMPORT_TASK_TEMPORARY_FILE:
                typeString = "导入任务临时文件";
                break;
            default:
                typeString = "未知类型的临时文件";
        }
        String taskId = Optional.ofNullable(taskKey).map(LongIdKey::getLongId).map(String::valueOf).orElse("null");
        return String.format("%s 不存在, 任务 ID 为 %s, 文件名为 %s", typeString, taskId, name);
    }
}
