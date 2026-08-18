package com.dwarfeng.fileio.stack.exception;

/**
 * 不支持的写入器类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UnsupportedWriterTypeException extends WriterException {

    private static final long serialVersionUID = 7541046648634644430L;

    private final String type;

    public UnsupportedWriterTypeException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的写入器类型: " + type;
    }
}
