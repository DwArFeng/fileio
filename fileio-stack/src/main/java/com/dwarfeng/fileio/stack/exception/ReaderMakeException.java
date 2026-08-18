package com.dwarfeng.fileio.stack.exception;

/**
 * 阅读器构造异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ReaderMakeException extends ReaderException {

    private static final long serialVersionUID = 5908739132906580660L;

    public ReaderMakeException() {
    }

    public ReaderMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReaderMakeException(String message) {
        super(message);
    }

    public ReaderMakeException(Throwable cause) {
        super(cause);
    }
}
