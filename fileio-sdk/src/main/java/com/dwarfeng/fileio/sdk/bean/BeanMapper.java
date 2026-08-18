package com.dwarfeng.fileio.sdk.bean;

import com.dwarfeng.fileio.sdk.bean.dto.*;
import com.dwarfeng.fileio.sdk.bean.entity.*;
import com.dwarfeng.fileio.sdk.bean.key.*;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.*;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.sdk.bean.key.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Mapper
public interface BeanMapper {

    // region Subgrade Key

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    JSFixedFastJsonLongIdKey longIdKeyToJSFixedFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromJSFixedFastJson(JSFixedFastJsonLongIdKey jSFixedFastJsonLongIdKey);

    WebInputLongIdKey longIdKeyToWebInput(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromWebInput(WebInputLongIdKey webInputLongIdKey);

    WebInputStringIdKey stringIdKeyToWebInput(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromWebInput(WebInputStringIdKey webInputStringIdKey);

    // endregion

    // region Fileio Key

    FastJsonTaskItemKey taskItemKeyToFastJson(TaskItemKey taskItemKey);

    @InheritInverseConfiguration
    TaskItemKey taskItemKeyFromFastJson(FastJsonTaskItemKey fastJsonTaskItemKey);

    FastJsonTaskSettingItemKey taskSettingItemKeyToFastJson(TaskSettingItemKey taskSettingItemKey);

    @InheritInverseConfiguration
    TaskSettingItemKey taskSettingItemKeyFromFastJson(FastJsonTaskSettingItemKey fastJsonTaskSettingItemKey);

    JSFixedFastJsonTaskItemKey taskItemKeyToJSFixedFastJson(TaskItemKey taskItemKey);

    @InheritInverseConfiguration
    TaskItemKey taskItemKeyFromJSFixedFastJson(JSFixedFastJsonTaskItemKey jsFixedFastJsonTaskItemKey);

    JSFixedFastJsonTaskSettingItemKey taskSettingItemKeyToJSFixedFastJson(TaskSettingItemKey taskSettingItemKey);

    @InheritInverseConfiguration
    TaskSettingItemKey taskSettingItemKeyFromJSFixedFastJson(
            JSFixedFastJsonTaskSettingItemKey jsFixedFastJsonTaskSettingItemKey
    );

    WebInputTaskItemKey taskItemKeyToWebInput(TaskItemKey taskItemKey);

    @InheritInverseConfiguration
    TaskItemKey taskItemKeyFromWebInput(WebInputTaskItemKey webInputTaskItemKey);

    WebInputTaskSettingItemKey taskSettingItemKeyToWebInput(TaskSettingItemKey taskSettingItemKey);

    @InheritInverseConfiguration
    TaskSettingItemKey taskSettingItemKeyFromWebInput(WebInputTaskSettingItemKey webInputTaskSettingItemKey);

    // endregion

    // region Fileio Entity

    FastJsonExportConf exportConfToFastJson(ExportConf exportConf);

    @InheritInverseConfiguration
    ExportConf exportConfFromFastJson(FastJsonExportConf fastJsonExportConf);

    FastJsonExporterInfo exporterInfoToFastJson(ExporterInfo exporterInfo);

    @InheritInverseConfiguration
    ExporterInfo exporterInfoFromFastJson(FastJsonExporterInfo fastJsonExporterInfo);

    FastJsonExporterSupport exporterSupportToFastJson(ExporterSupport exporterSupport);

    @InheritInverseConfiguration
    ExporterSupport exporterSupportFromFastJson(FastJsonExporterSupport fastJsonExporterSupport);

    FastJsonExportFileInfo exportFileInfoToFastJson(ExportFileInfo exportFileInfo);

    @InheritInverseConfiguration
    ExportFileInfo exportFileInfoFromFastJson(FastJsonExportFileInfo fastJsonExportFileInfo);

    FastJsonExportTask exportTaskToFastJson(ExportTask exportTask);

    @InheritInverseConfiguration
    ExportTask exportTaskFromFastJson(FastJsonExportTask fastJsonExportTask);

    FastJsonExportTaskSetting exportTaskSettingToFastJson(ExportTaskSetting exportTaskSetting);

    @InheritInverseConfiguration
    ExportTaskSetting exportTaskSettingFromFastJson(FastJsonExportTaskSetting fastJsonExportTaskSetting);

    FastJsonExportTemplateInfo exportTemplateInfoToFastJson(ExportTemplateInfo exportTemplateInfo);

    @InheritInverseConfiguration
    ExportTemplateInfo exportTemplateInfoFromFastJson(FastJsonExportTemplateInfo fastJsonExportTemplateInfo);

    FastJsonImportConf importConfToFastJson(ImportConf importConf);

    @InheritInverseConfiguration
    ImportConf importConfFromFastJson(FastJsonImportConf fastJsonImportConf);

    FastJsonImporterInfo importerInfoToFastJson(ImporterInfo importerInfo);

    @InheritInverseConfiguration
    ImporterInfo importerInfoFromFastJson(FastJsonImporterInfo fastJsonImporterInfo);

    FastJsonImporterSupport importerSupportToFastJson(ImporterSupport importerSupport);

    @InheritInverseConfiguration
    ImporterSupport importerSupportFromFastJson(FastJsonImporterSupport fastJsonImporterSupport);

    FastJsonImportFileInfo importFileInfoToFastJson(ImportFileInfo importFileInfo);

    @InheritInverseConfiguration
    ImportFileInfo importFileInfoFromFastJson(FastJsonImportFileInfo fastJsonImportFileInfo);

    FastJsonImportTask importTaskToFastJson(ImportTask importTask);

    @InheritInverseConfiguration
    ImportTask importTaskFromFastJson(FastJsonImportTask fastJsonImportTask);

    FastJsonImportTaskSetting importTaskSettingToFastJson(ImportTaskSetting importTaskSetting);

    @InheritInverseConfiguration
    ImportTaskSetting importTaskSettingFromFastJson(FastJsonImportTaskSetting fastJsonImportTaskSetting);

    FastJsonImportTemplateInfo importTemplateInfoToFastJson(ImportTemplateInfo importTemplateInfo);

    @InheritInverseConfiguration
    ImportTemplateInfo importTemplateInfoFromFastJson(FastJsonImportTemplateInfo fastJsonImportTemplateInfo);

    FastJsonReaderInfo readerInfoToFastJson(ReaderInfo readerInfo);

    @InheritInverseConfiguration
    ReaderInfo readerInfoFromFastJson(FastJsonReaderInfo fastJsonReaderInfo);

    FastJsonReaderSupport readerSupportToFastJson(ReaderSupport readerSupport);

    @InheritInverseConfiguration
    ReaderSupport readerSupportFromFastJson(FastJsonReaderSupport fastJsonReaderSupport);

    FastJsonWriterInfo writerInfoToFastJson(WriterInfo writerInfo);

    @InheritInverseConfiguration
    WriterInfo writerInfoFromFastJson(FastJsonWriterInfo fastJsonWriterInfo);

    FastJsonWriterSupport writerSupportToFastJson(WriterSupport writerSupport);

    @InheritInverseConfiguration
    WriterSupport writerSupportFromFastJson(FastJsonWriterSupport fastJsonWriterSupport);

    FastJsonExportMetadata exportMetadataToFastJson(ExportMetadata exportMetadata);

    @InheritInverseConfiguration
    ExportMetadata exportMetadataFromFastJson(FastJsonExportMetadata fastJsonExportMetadata);

    FastJsonImportMetadata importMetadataToFastJson(ImportMetadata importMetadata);

    @InheritInverseConfiguration
    ImportMetadata importMetadataFromFastJson(FastJsonImportMetadata fastJsonImportMetadata);

    JSFixedFastJsonExportConf exportConfToJSFixedFastJson(ExportConf exportConf);

    @InheritInverseConfiguration
    ExportConf exportConfFromJSFixedFastJson(JSFixedFastJsonExportConf jSFixedFastJsonExportConf);

    JSFixedFastJsonExporterInfo exporterInfoToJSFixedFastJson(ExporterInfo exporterInfo);

    @InheritInverseConfiguration
    ExporterInfo exporterInfoFromJSFixedFastJson(JSFixedFastJsonExporterInfo jSFixedFastJsonExporterInfo);

    JSFixedFastJsonExportFileInfo exportFileInfoToJSFixedFastJson(ExportFileInfo exportFileInfo);

    @InheritInverseConfiguration
    ExportFileInfo exportFileInfoFromJSFixedFastJson(JSFixedFastJsonExportFileInfo jSFixedFastJsonExportFileInfo);

    JSFixedFastJsonExportTask exportTaskToJSFixedFastJson(ExportTask exportTask);

    @InheritInverseConfiguration
    ExportTask exportTaskFromJSFixedFastJson(JSFixedFastJsonExportTask jSFixedFastJsonExportTask);

    JSFixedFastJsonExportTaskSetting exportTaskSettingToJSFixedFastJson(ExportTaskSetting exportTaskSetting);

    @InheritInverseConfiguration
    ExportTaskSetting exportTaskSettingFromJSFixedFastJson(
            JSFixedFastJsonExportTaskSetting jSFixedFastJsonExportTaskSetting
    );

    JSFixedFastJsonExportTemplateInfo exportTemplateInfoToJSFixedFastJson(ExportTemplateInfo exportTemplateInfo);

    @InheritInverseConfiguration
    ExportTemplateInfo exportTemplateInfoFromJSFixedFastJson(
            JSFixedFastJsonExportTemplateInfo jSFixedFastJsonExportTemplateInfo
    );

    JSFixedFastJsonImportConf importConfToJSFixedFastJson(ImportConf importConf);

    @InheritInverseConfiguration
    ImportConf importConfFromJSFixedFastJson(JSFixedFastJsonImportConf jSFixedFastJsonImportConf);

    JSFixedFastJsonImporterInfo importerInfoToJSFixedFastJson(ImporterInfo importerInfo);

    @InheritInverseConfiguration
    ImporterInfo importerInfoFromJSFixedFastJson(JSFixedFastJsonImporterInfo jSFixedFastJsonImporterInfo);

    JSFixedFastJsonImportFileInfo importFileInfoToJSFixedFastJson(ImportFileInfo importFileInfo);

    @InheritInverseConfiguration
    ImportFileInfo importFileInfoFromJSFixedFastJson(JSFixedFastJsonImportFileInfo jSFixedFastJsonImportFileInfo);

    JSFixedFastJsonImportTask importTaskToJSFixedFastJson(ImportTask importTask);

    @InheritInverseConfiguration
    ImportTask importTaskFromJSFixedFastJson(JSFixedFastJsonImportTask jSFixedFastJsonImportTask);

    JSFixedFastJsonImportTaskSetting importTaskSettingToJSFixedFastJson(ImportTaskSetting importTaskSetting);

    @InheritInverseConfiguration
    ImportTaskSetting importTaskSettingFromJSFixedFastJson(
            JSFixedFastJsonImportTaskSetting jSFixedFastJsonImportTaskSetting
    );

    JSFixedFastJsonImportTemplateInfo importTemplateInfoToJSFixedFastJson(ImportTemplateInfo importTemplateInfo);

    @InheritInverseConfiguration
    ImportTemplateInfo importTemplateInfoFromJSFixedFastJson(
            JSFixedFastJsonImportTemplateInfo jSFixedFastJsonImportTemplateInfo
    );

    JSFixedFastJsonReaderInfo readerInfoToJSFixedFastJson(ReaderInfo readerInfo);

    @InheritInverseConfiguration
    ReaderInfo readerInfoFromJSFixedFastJson(JSFixedFastJsonReaderInfo jSFixedFastJsonReaderInfo);

    JSFixedFastJsonWriterInfo writerInfoToJSFixedFastJson(WriterInfo writerInfo);

    @InheritInverseConfiguration
    WriterInfo writerInfoFromJSFixedFastJson(JSFixedFastJsonWriterInfo jSFixedFastJsonWriterInfo);

    JSFixedFastJsonExportMetadata exportMetadataToJSFixedFastJson(ExportMetadata exportMetadata);

    @InheritInverseConfiguration
    ExportMetadata exportMetadataFromJSFixedFastJson(JSFixedFastJsonExportMetadata jSFixedFastJsonExportMetadata);

    JSFixedFastJsonImportMetadata importMetadataToJSFixedFastJson(ImportMetadata importMetadata);

    @InheritInverseConfiguration
    ImportMetadata importMetadataFromJSFixedFastJson(JSFixedFastJsonImportMetadata jSFixedFastJsonImportMetadata);

    WebInputExportConf exportConfToWebInput(ExportConf exportConf);

    @InheritInverseConfiguration
    ExportConf exportConfFromWebInput(WebInputExportConf webInputExportConf);

    WebInputExporterInfo exporterInfoToWebInput(ExporterInfo exporterInfo);

    @InheritInverseConfiguration
    ExporterInfo exporterInfoFromWebInput(WebInputExporterInfo webInputExporterInfo);

    WebInputExportMetadata exportMetadataToWebInput(ExportMetadata exportMetadata);

    @InheritInverseConfiguration
    ExportMetadata exportMetadataFromWebInput(WebInputExportMetadata webInputExportMetadata);

    WebInputExportTaskSetting exportTaskSettingToWebInput(ExportTaskSetting exportTaskSetting);

    @InheritInverseConfiguration
    ExportTaskSetting exportTaskSettingFromWebInput(WebInputExportTaskSetting webInputExportTaskSetting);

    WebInputImportConf importConfToWebInput(ImportConf importConf);

    @InheritInverseConfiguration
    ImportConf importConfFromWebInput(WebInputImportConf webInputImportConf);

    WebInputImporterInfo importerInfoToWebInput(ImporterInfo importerInfo);

    @InheritInverseConfiguration
    ImporterInfo importerInfoFromWebInput(WebInputImporterInfo webInputImporterInfo);

    WebInputImportMetadata importMetadataToWebInput(ImportMetadata importMetadata);

    @InheritInverseConfiguration
    ImportMetadata importMetadataFromWebInput(WebInputImportMetadata webInputImportMetadata);

    WebInputImportTaskSetting importTaskSettingToWebInput(ImportTaskSetting importTaskSetting);

    @InheritInverseConfiguration
    ImportTaskSetting importTaskSettingFromWebInput(WebInputImportTaskSetting webInputImportTaskSetting);

    WebInputReaderInfo readerInfoToWebInput(ReaderInfo readerInfo);

    @InheritInverseConfiguration
    ReaderInfo readerInfoFromWebInput(WebInputReaderInfo webInputReaderInfo);

    WebInputWriterInfo writerInfoToWebInput(WriterInfo writerInfo);

    @InheritInverseConfiguration
    WriterInfo writerInfoFromWebInput(WebInputWriterInfo webInputWriterInfo);

    FastJsonUser userToFastJson(User user);

    @InheritInverseConfiguration
    User userFromFastJson(FastJsonUser fastJsonUser);

    WebInputUser userToWebInput(User user);

    @InheritInverseConfiguration
    User userFromWebInput(WebInputUser webInputUser);

    // endregion

    // region Fileio Operate Dto

    WebInputFileCreateInfo fileCreateInfoToWebInput(FileCreateInfo fileCreateInfo);

    @InheritInverseConfiguration
    FileCreateInfo fileCreateInfoFromWebInput(WebInputFileCreateInfo webInputFileCreateInfo);

    WebInputFileDownloadInfo fileDownloadInfoToWebInput(FileDownloadInfo fileDownloadInfo);

    @InheritInverseConfiguration
    FileDownloadInfo fileDownloadInfoFromWebInput(WebInputFileDownloadInfo webInputFileDownloadInfo);

    WebInputFileRemoveInfo fileRemoveInfoToWebInput(FileRemoveInfo fileRemoveInfo);

    @InheritInverseConfiguration
    FileRemoveInfo fileRemoveInfoFromWebInput(WebInputFileRemoveInfo webInputFileRemoveInfo);

    WebInputFileUploadInfo fileUploadInfoToWebInput(FileUploadInfo fileUploadInfo);

    @InheritInverseConfiguration
    FileUploadInfo fileUploadInfoFromWebInput(WebInputFileUploadInfo webInputFileUploadInfo);

    WebInputMetadataUpsertInfo metadataUpsertInfoToWebInput(MetadataUpsertInfo metadataUpsertInfo);

    @InheritInverseConfiguration
    MetadataUpsertInfo metadataUpsertInfoFromWebInput(WebInputMetadataUpsertInfo webInputMetadataUpsertInfo);

    WebInputTaskCreateInfo taskCreateInfoToWebInput(TaskCreateInfo taskCreateInfo);

    @InheritInverseConfiguration
    TaskCreateInfo taskCreateInfoFromWebInput(WebInputTaskCreateInfo webInputTaskCreateInfo);

    WebInputTaskExecuteInfo taskExecuteInfoToWebInput(TaskExecuteInfo taskExecuteInfo);

    @InheritInverseConfiguration
    TaskExecuteInfo taskExecuteInfoFromWebInput(WebInputTaskExecuteInfo webInputTaskExecuteInfo);

    WebInputTaskStartInfo taskStartInfoToWebInput(TaskStartInfo taskStartInfo);

    @InheritInverseConfiguration
    TaskStartInfo taskStartInfoFromWebInput(WebInputTaskStartInfo webInputTaskStartInfo);

    WebInputTemplateCreateInfo templateCreateInfoToWebInput(TemplateCreateInfo templateCreateInfo);

    @InheritInverseConfiguration
    TemplateCreateInfo templateCreateInfoFromWebInput(WebInputTemplateCreateInfo webInputTemplateCreateInfo);

    WebInputTemplateDownloadInfo templateDownloadInfoToWebInput(TemplateDownloadInfo templateDownloadInfo);

    @InheritInverseConfiguration
    TemplateDownloadInfo templateDownloadInfoFromWebInput(WebInputTemplateDownloadInfo webInputTemplateDownloadInfo);

    WebInputTemplateRemoveInfo templateRemoveInfoToWebInput(TemplateRemoveInfo templateRemoveInfo);

    @InheritInverseConfiguration
    TemplateRemoveInfo templateRemoveInfoFromWebInput(WebInputTemplateRemoveInfo webInputTemplateRemoveInfo);

    WebInputTemplateUploadInfo templateUploadInfoToWebInput(TemplateUploadInfo templateUploadInfo);

    @InheritInverseConfiguration
    TemplateUploadInfo templateUploadInfoFromWebInput(WebInputTemplateUploadInfo webInputTemplateUploadInfo);

    FastJsonPurgeFinishedResult purgeFinishedResultToWebInput(PurgeFinishedResult purgeFinishedResult);

    @InheritInverseConfiguration
    PurgeFinishedResult purgeFinishedResultFromWebInput(FastJsonPurgeFinishedResult fastJsonPurgeFinishedResult);

    // endregion
}
