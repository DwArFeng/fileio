package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 临时文件异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemporaryFileException extends HandlerException {

    private static final long serialVersionUID = -2281489181975176853L;

    public TemporaryFileException() {
    }

    public TemporaryFileException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "临时文件异常";
    }
}
