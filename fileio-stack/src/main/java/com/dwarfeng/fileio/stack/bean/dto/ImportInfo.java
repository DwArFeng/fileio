package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.entity.*;
import com.dwarfeng.fileio.stack.handler.Importer;
import com.dwarfeng.fileio.stack.handler.Writer;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Map;

/**
 * 导入信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportInfo implements Dto {

    private static final long serialVersionUID = 4800660298405610240L;

    private ImportTaskSetting importTaskSetting;
    private Map<String, ImporterInfo> importerInfoMap;
    private Map<String, Importer> importerMap;
    private Map<String, WriterInfo> writerInfoMap;
    private Map<String, Writer> writerMap;
    private Map<String, ImportConf> importConfMap;
    private Map<String, ImportTemplateInfo> importTemplateInfoMap;

    public ImportInfo() {
    }

    public ImportInfo(
            ImportTaskSetting importTaskSetting,
            Map<String, ImporterInfo> importerInfoMap, Map<String, Importer> importerMap,
            Map<String, WriterInfo> writerInfoMap, Map<String, Writer> writerMap,
            Map<String, ImportConf> importConfMap,
            Map<String, ImportTemplateInfo> importTemplateInfoMap
    ) {
        this.importTaskSetting = importTaskSetting;
        this.importerInfoMap = importerInfoMap;
        this.importerMap = importerMap;
        this.writerInfoMap = writerInfoMap;
        this.writerMap = writerMap;
        this.importConfMap = importConfMap;
        this.importTemplateInfoMap = importTemplateInfoMap;
    }

    public ImportTaskSetting getImportTaskSetting() {
        return importTaskSetting;
    }

    public void setImportTaskSetting(ImportTaskSetting importTaskSetting) {
        this.importTaskSetting = importTaskSetting;
    }

    public Map<String, ImporterInfo> getImporterInfoMap() {
        return importerInfoMap;
    }

    public void setImporterInfoMap(Map<String, ImporterInfo> importerInfoMap) {
        this.importerInfoMap = importerInfoMap;
    }

    public Map<String, Importer> getImporterMap() {
        return importerMap;
    }

    public void setImporterMap(Map<String, Importer> importerMap) {
        this.importerMap = importerMap;
    }

    public Map<String, WriterInfo> getWriterInfoMap() {
        return writerInfoMap;
    }

    public void setWriterInfoMap(Map<String, WriterInfo> writerInfoMap) {
        this.writerInfoMap = writerInfoMap;
    }

    public Map<String, Writer> getWriterMap() {
        return writerMap;
    }

    public void setWriterMap(Map<String, Writer> writerMap) {
        this.writerMap = writerMap;
    }

    public Map<String, ImportConf> getImportConfMap() {
        return importConfMap;
    }

    public void setImportConfMap(Map<String, ImportConf> importConfMap) {
        this.importConfMap = importConfMap;
    }

    public Map<String, ImportTemplateInfo> getImportTemplateInfoMap() {
        return importTemplateInfoMap;
    }

    public void setImportTemplateInfoMap(Map<String, ImportTemplateInfo> importTemplateInfoMap) {
        this.importTemplateInfoMap = importTemplateInfoMap;
    }

    @Override
    public String toString() {
        return "ImportInfo{" +
                "importTaskSetting=" + importTaskSetting +
                ", importerInfoMap=" + importerInfoMap +
                ", importerMap=" + importerMap +
                ", writerInfoMap=" + writerInfoMap +
                ", writerMap=" + writerMap +
                ", importConfMap=" + importConfMap +
                ", importTemplateInfoMap=" + importTemplateInfoMap +
                '}';
    }
}
