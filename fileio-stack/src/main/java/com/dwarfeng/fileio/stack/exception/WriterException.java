package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 写入器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WriterException extends HandlerException {

    private static final long serialVersionUID = 6641529833862589893L;

    public WriterException() {
    }

    public WriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriterException(String message) {
        super(message);
    }

    public WriterException(Throwable cause) {
        super(cause);
    }
}
