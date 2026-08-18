package com.dwarfeng.fileio.sdk.handler.importer;

import com.dwarfeng.fileio.sdk.handler.ImporterMaker;
import com.dwarfeng.fileio.sdk.handler.ImporterSupporter;

import java.util.Objects;

/**
 * 抽象导入器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractImporterRegistry implements ImporterMaker, ImporterSupporter {

    protected String importerType;

    public AbstractImporterRegistry() {
    }

    public AbstractImporterRegistry(String importerType) {
        this.importerType = importerType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(importerType, type);
    }

    @Override
    public String provideType() {
        return importerType;
    }

    public String getImporterType() {
        return importerType;
    }

    public void setImporterType(String importerType) {
        this.importerType = importerType;
    }

    @Override
    public String toString() {
        return "AbstractImporterRegistry{" +
                "importerType='" + importerType + '\'' +
                '}';
    }
}
