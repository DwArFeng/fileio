package com.dwarfeng.fileio.impl.bean;

import com.dwarfeng.fileio.impl.bean.entity.*;
import com.dwarfeng.fileio.impl.bean.key.HibernateTaskItemKey;
import com.dwarfeng.fileio.impl.bean.key.HibernateTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.entity.*;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Hibernate Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Mapper
public interface BeanMapper {

    // region Subgrade Key

    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    HibernateStringIdKey stringIdKeyToHibernate(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromHibernate(HibernateStringIdKey hibernateStringIdKey);

    // endregion

    // region Fileio Key

    HibernateTaskItemKey taskItemKeyToHibernate(TaskItemKey taskItemKey);

    @InheritInverseConfiguration
    TaskItemKey taskItemKeyFromHibernate(HibernateTaskItemKey hibernateTaskItemKey);

    HibernateTaskSettingItemKey taskSettingItemKeyToHibernate(TaskSettingItemKey taskSettingItemKey);

    @InheritInverseConfiguration
    TaskSettingItemKey taskSettingItemKeyFromHibernate(HibernateTaskSettingItemKey hibernateTaskSettingItemKey);

    // endregion

    // region Fileio Entity

    @Mapping(target = "taskSettingId", ignore = true)
    @Mapping(target = "taskSetting", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateExportConf exportConfToHibernate(ExportConf exportConf);

    @InheritInverseConfiguration
    ExportConf exportConfFromHibernate(HibernateExportConf hibernateExportConf);

    @Mapping(target = "taskSettingId", ignore = true)
    @Mapping(target = "taskSetting", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateExporterInfo exporterInfoToHibernate(ExporterInfo exporterInfo);

    @InheritInverseConfiguration
    ExporterInfo exporterInfoFromHibernate(HibernateExporterInfo hibernateExporterInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateExporterSupport exporterSupportToHibernate(ExporterSupport exporterSupport);

    @InheritInverseConfiguration
    ExporterSupport exporterSupportFromHibernate(HibernateExporterSupport hibernateExporterSupport);

    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    HibernateExportFileInfo exportFileInfoToHibernate(ExportFileInfo exportFileInfo);

    @InheritInverseConfiguration
    ExportFileInfo exportFileInfoFromHibernate(HibernateExportFileInfo hibernateExportFileInfo);

    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    HibernateExportMetadata exportMetadataToHibernate(ExportMetadata exportMetadata);

    @InheritInverseConfiguration
    ExportMetadata exportMetadataFromHibernate(HibernateExportMetadata hibernateExportMetadata);

    @Mapping(target = "userStringId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "taskSetting", ignore = true)
    @Mapping(target = "settingLongId", ignore = true)
    @Mapping(target = "metadatas", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "fileInfos", ignore = true)
    HibernateExportTask exportTaskToHibernate(ExportTask exportTask);

    @InheritInverseConfiguration
    ExportTask exportTaskFromHibernate(HibernateExportTask hibernateExportTask);

    @Mapping(target = "templateInfos", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "readerInfos", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "exporterInfos", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "confs", ignore = true)
    HibernateExportTaskSetting exportTaskSettingToHibernate(ExportTaskSetting exportTaskSetting);

    @InheritInverseConfiguration
    ExportTaskSetting exportTaskSettingFromHibernate(HibernateExportTaskSetting hibernateExportTaskSetting);

    @Mapping(target = "taskSettingId", ignore = true)
    @Mapping(target = "taskSetting", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateExportTemplateInfo exportTemplateInfoToHibernate(ExportTemplateInfo exportTemplateInfo);

    @InheritInverseConfiguration
    ExportTemplateInfo exportTemplateInfoFromHibernate(HibernateExportTemplateInfo hibernateExportTemplateInfo);

    @Mapping(target = "taskSettingId", ignore = true)
    @Mapping(target = "taskSetting", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateImportConf importConfToHibernate(ImportConf importConf);

    @InheritInverseConfiguration
    ImportConf importConfFromHibernate(HibernateImportConf hibernateImportConf);

    @Mapping(target = "taskSettingId", ignore = true)
    @Mapping(target = "taskSetting", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateImporterInfo importerInfoToHibernate(ImporterInfo importerInfo);

    @InheritInverseConfiguration
    ImporterInfo importerInfoFromHibernate(HibernateImporterInfo hibernateImporterInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateImporterSupport importerSupportToHibernate(ImporterSupport importerSupport);

    @InheritInverseConfiguration
    ImporterSupport importerSupportFromHibernate(HibernateImporterSupport hibernateImporterSupport);

    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    HibernateImportFileInfo importFileInfoToHibernate(ImportFileInfo importFileInfo);

    @InheritInverseConfiguration
    ImportFileInfo importFileInfoFromHibernate(HibernateImportFileInfo hibernateImportFileInfo);

    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    HibernateImportMetadata importMetadataToHibernate(ImportMetadata importMetadata);

    @InheritInverseConfiguration
    ImportMetadata importMetadataFromHibernate(HibernateImportMetadata hibernateImportMetadata);

    @Mapping(target = "userStringId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "taskSetting", ignore = true)
    @Mapping(target = "settingLongId", ignore = true)
    @Mapping(target = "metadatas", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "fileInfos", ignore = true)
    HibernateImportTask importTaskToHibernate(ImportTask importTask);

    @InheritInverseConfiguration
    ImportTask importTaskFromHibernate(HibernateImportTask hibernateImportTask);

    @Mapping(target = "writerInfos", ignore = true)
    @Mapping(target = "templateInfos", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "importerInfos", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "confs", ignore = true)
    HibernateImportTaskSetting importTaskSettingToHibernate(ImportTaskSetting importTaskSetting);

    @InheritInverseConfiguration
    ImportTaskSetting importTaskSettingFromHibernate(HibernateImportTaskSetting hibernateImportTaskSetting);

    @Mapping(target = "taskSettingId", ignore = true)
    @Mapping(target = "taskSetting", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateImportTemplateInfo importTemplateInfoToHibernate(ImportTemplateInfo importTemplateInfo);

    @InheritInverseConfiguration
    ImportTemplateInfo importTemplateInfoFromHibernate(HibernateImportTemplateInfo hibernateImportTemplateInfo);

    @Mapping(target = "taskSettingId", ignore = true)
    @Mapping(target = "taskSetting", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateReaderInfo readerInfoToHibernate(ReaderInfo readerInfo);

    @InheritInverseConfiguration
    ReaderInfo readerInfoFromHibernate(HibernateReaderInfo hibernateReaderInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateReaderSupport readerSupportToHibernate(ReaderSupport readerSupport);

    @InheritInverseConfiguration
    ReaderSupport readerSupportFromHibernate(HibernateReaderSupport hibernateReaderSupport);

    @Mapping(target = "taskSettingId", ignore = true)
    @Mapping(target = "taskSetting", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateWriterInfo writerInfoToHibernate(WriterInfo writerInfo);

    @InheritInverseConfiguration
    WriterInfo writerInfoFromHibernate(HibernateWriterInfo hibernateWriterInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateWriterSupport writerSupportToHibernate(WriterSupport writerSupport);

    @InheritInverseConfiguration
    WriterSupport writerSupportFromHibernate(HibernateWriterSupport hibernateWriterSupport);

    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "importTasks", ignore = true)
    @Mapping(target = "exportTasks", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateUser userToHibernate(User user);

    @InheritInverseConfiguration
    User userFromHibernate(HibernateUser hibernateUser);

    // endregion
}
