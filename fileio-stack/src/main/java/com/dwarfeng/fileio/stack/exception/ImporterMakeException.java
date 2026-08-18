package com.dwarfeng.fileio.stack.exception;

/**
 * 导入器构造异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImporterMakeException extends ImporterException {

    private static final long serialVersionUID = -6138840550270710387L;

    public ImporterMakeException() {
    }

    public ImporterMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImporterMakeException(String message) {
        super(message);
    }

    public ImporterMakeException(Throwable cause) {
        super(cause);
    }
}
