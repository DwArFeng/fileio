package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 导入器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImporterException extends HandlerException {

    private static final long serialVersionUID = 3340941771457607341L;

    public ImporterException() {
    }

    public ImporterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImporterException(String message) {
        super(message);
    }

    public ImporterException(Throwable cause) {
        super(cause);
    }
}
