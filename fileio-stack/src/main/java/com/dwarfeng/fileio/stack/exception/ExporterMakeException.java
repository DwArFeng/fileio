package com.dwarfeng.fileio.stack.exception;

/**
 * 导出器构造异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExporterMakeException extends ExporterException {

    private static final long serialVersionUID = -7687879891173974745L;

    public ExporterMakeException() {
    }

    public ExporterMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExporterMakeException(String message) {
        super(message);
    }

    public ExporterMakeException(Throwable cause) {
        super(cause);
    }
}
