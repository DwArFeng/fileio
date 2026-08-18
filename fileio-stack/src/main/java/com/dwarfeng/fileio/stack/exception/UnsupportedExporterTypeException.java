package com.dwarfeng.fileio.stack.exception;

/**
 * 不支持的导出器类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UnsupportedExporterTypeException extends ExporterException {

    private static final long serialVersionUID = 3434762683763268570L;

    private final String type;

    public UnsupportedExporterTypeException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的导出器类型: " + type;
    }
}
