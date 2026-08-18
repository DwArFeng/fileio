package com.dwarfeng.fileio.sdk.handler.reader;

import com.dwarfeng.fileio.sdk.handler.ReaderMaker;
import com.dwarfeng.fileio.sdk.handler.ReaderSupporter;

import java.util.Objects;

/**
 * 抽象读取器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractReaderRegistry implements ReaderMaker, ReaderSupporter {

    protected String readerType;

    public AbstractReaderRegistry() {
    }

    public AbstractReaderRegistry(String readerType) {
        this.readerType = readerType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(readerType, type);
    }

    @Override
    public String provideType() {
        return readerType;
    }

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    @Override
    public String toString() {
        return "AbstractReaderRegistry{" +
                "readerType='" + readerType + '\'' +
                '}';
    }
}
