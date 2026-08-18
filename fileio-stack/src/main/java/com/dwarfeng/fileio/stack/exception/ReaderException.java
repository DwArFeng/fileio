package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 阅读器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ReaderException extends HandlerException {

    private static final long serialVersionUID = -7071409219498016612L;

    public ReaderException() {
    }

    public ReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReaderException(String message) {
        super(message);
    }

    public ReaderException(Throwable cause) {
        super(cause);
    }
}
