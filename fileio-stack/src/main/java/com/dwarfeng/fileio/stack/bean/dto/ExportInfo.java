package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.entity.*;
import com.dwarfeng.fileio.stack.handler.Exporter;
import com.dwarfeng.fileio.stack.handler.Reader;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Map;

/**
 * 导出信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExportInfo implements Dto {

    private static final long serialVersionUID = 3368602763009723073L;

    private ExportTaskSetting exportTaskSetting;
    private Map<String, ExporterInfo> exporterInfoMap;
    private Map<String, Exporter> exporterMap;
    private Map<String, ReaderInfo> readerInfoMap;
    private Map<String, Reader> readerMap;
    private Map<String, ExportConf> exportConfMap;
    private Map<String, ExportTemplateInfo> exportTemplateInfoMap;

    public ExportInfo() {
    }

    public ExportInfo(
            ExportTaskSetting exportTaskSetting,
            Map<String, ExporterInfo> exporterInfoMap, Map<String, Exporter> exporterMap,
            Map<String, ReaderInfo> readerInfoMap, Map<String, Reader> readerMap,
            Map<String, ExportConf> exportConfMap,
            Map<String, ExportTemplateInfo> exportTemplateInfoMap
    ) {
        this.exportTaskSetting = exportTaskSetting;
        this.exporterInfoMap = exporterInfoMap;
        this.exporterMap = exporterMap;
        this.readerInfoMap = readerInfoMap;
        this.readerMap = readerMap;
        this.exportConfMap = exportConfMap;
        this.exportTemplateInfoMap = exportTemplateInfoMap;
    }

    public ExportTaskSetting getExportTaskSetting() {
        return exportTaskSetting;
    }

    public void setExportTaskSetting(ExportTaskSetting exportTaskSetting) {
        this.exportTaskSetting = exportTaskSetting;
    }

    public Map<String, ExporterInfo> getExporterInfoMap() {
        return exporterInfoMap;
    }

    public void setExporterInfoMap(Map<String, ExporterInfo> exporterInfoMap) {
        this.exporterInfoMap = exporterInfoMap;
    }

    public Map<String, Exporter> getExporterMap() {
        return exporterMap;
    }

    public void setExporterMap(Map<String, Exporter> exporterMap) {
        this.exporterMap = exporterMap;
    }

    public Map<String, ReaderInfo> getReaderInfoMap() {
        return readerInfoMap;
    }

    public void setReaderInfoMap(Map<String, ReaderInfo> readerInfoMap) {
        this.readerInfoMap = readerInfoMap;
    }

    public Map<String, Reader> getReaderMap() {
        return readerMap;
    }

    public void setReaderMap(Map<String, Reader> readerMap) {
        this.readerMap = readerMap;
    }

    public Map<String, ExportConf> getExportConfMap() {
        return exportConfMap;
    }

    public void setExportConfMap(Map<String, ExportConf> exportConfMap) {
        this.exportConfMap = exportConfMap;
    }

    public Map<String, ExportTemplateInfo> getExportTemplateInfoMap() {
        return exportTemplateInfoMap;
    }

    public void setExportTemplateInfoMap(Map<String, ExportTemplateInfo> exportTemplateInfoMap) {
        this.exportTemplateInfoMap = exportTemplateInfoMap;
    }

    @Override
    public String toString() {
        return "ExportInfo{" +
                "exportTaskSetting=" + exportTaskSetting +
                ", exporterInfoMap=" + exporterInfoMap +
                ", exporterMap=" + exporterMap +
                ", readerInfoMap=" + readerInfoMap +
                ", readerMap=" + readerMap +
                ", exportConfMap=" + exportConfMap +
                ", exportTemplateInfoMap=" + exportTemplateInfoMap +
                '}';
    }
}
