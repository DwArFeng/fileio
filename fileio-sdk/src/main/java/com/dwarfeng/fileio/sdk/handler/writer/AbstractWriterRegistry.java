package com.dwarfeng.fileio.sdk.handler.writer;

import com.dwarfeng.fileio.sdk.handler.WriterMaker;
import com.dwarfeng.fileio.sdk.handler.WriterSupporter;

import java.util.Objects;

/**
 * 抽象写入器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractWriterRegistry implements WriterMaker, WriterSupporter {

    protected String writerType;

    public AbstractWriterRegistry() {
    }

    public AbstractWriterRegistry(String writerType) {
        this.writerType = writerType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(writerType, type);
    }

    @Override
    public String provideType() {
        return writerType;
    }

    public String getWriterType() {
        return writerType;
    }

    public void setWriterType(String writerType) {
        this.writerType = writerType;
    }

    @Override
    public String toString() {
        return "AbstractWriterRegistry{" +
                "writerType='" + writerType + '\'' +
                '}';
    }
}
