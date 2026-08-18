package com.dwarfeng.fileio.stack.exception;

/**
 * 不支持的阅读器类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UnsupportedReaderTypeException extends ReaderException {

    private static final long serialVersionUID = -7916305201502237795L;

    private final String type;

    public UnsupportedReaderTypeException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的阅读器类型: " + type;
    }
}
