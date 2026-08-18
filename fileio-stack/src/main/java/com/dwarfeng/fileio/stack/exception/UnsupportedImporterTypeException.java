package com.dwarfeng.fileio.stack.exception;

/**
 * 不支持的导入器类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UnsupportedImporterTypeException extends ImporterException {

    private static final long serialVersionUID = -962468824329623232L;

    private final String type;

    public UnsupportedImporterTypeException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的导入器类型: " + type;
    }
}
