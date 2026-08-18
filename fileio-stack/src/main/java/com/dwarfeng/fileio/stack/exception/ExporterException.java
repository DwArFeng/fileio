package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导出器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExporterException extends HandlerException {

    private static final long serialVersionUID = 8149799079233641163L;

    public ExporterException() {
    }

    public ExporterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExporterException(String message) {
        super(message);
    }

    public ExporterException(Throwable cause) {
        super(cause);
    }
}
