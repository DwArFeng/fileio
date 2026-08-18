package com.dwarfeng.fileio.stack.exception;

/**
 * 写入器构造异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WriterMakeException extends WriterException {

    private static final long serialVersionUID = 880335539525273499L;

    public WriterMakeException() {
    }

    public WriterMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriterMakeException(String message) {
        super(message);
    }

    public WriterMakeException(Throwable cause) {
        super(cause);
    }
}
