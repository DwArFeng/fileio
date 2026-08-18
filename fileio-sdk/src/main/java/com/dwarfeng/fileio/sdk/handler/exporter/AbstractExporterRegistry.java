package com.dwarfeng.fileio.sdk.handler.exporter;

import com.dwarfeng.fileio.sdk.handler.ExporterMaker;
import com.dwarfeng.fileio.sdk.handler.ExporterSupporter;

import java.util.Objects;

/**
 * 抽象导出器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractExporterRegistry implements ExporterMaker, ExporterSupporter {

    protected String exporterType;

    public AbstractExporterRegistry() {
    }

    public AbstractExporterRegistry(String exporterType) {
        this.exporterType = exporterType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(exporterType, type);
    }

    @Override
    public String provideType() {
        return exporterType;
    }

    public String getExporterType() {
        return exporterType;
    }

    public void setExporterType(String exporterType) {
        this.exporterType = exporterType;
    }

    @Override
    public String toString() {
        return "AbstractExporterRegistry{" +
                "exporterType='" + exporterType + '\'' +
                '}';
    }
}
