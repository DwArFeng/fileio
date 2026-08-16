package com.dwarfeng.fileio.node.all.he.configuration;

import com.dwarfeng.fileio.impl.service.operation.*;
import com.dwarfeng.fileio.stack.bean.entity.*;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.cache.*;
import com.dwarfeng.fileio.stack.dao.*;
import com.dwarfeng.subgrade.impl.generation.ExceptionKeyGenerator;
import com.dwarfeng.subgrade.impl.service.CustomBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralBatchCrudService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;
    private final GenerateConfiguration generateConfiguration;

    private final ExportConfDao exportConfDao;
    private final ExportConfCache exportConfCache;
    private final ExporterInfoDao exporterInfoDao;
    private final ExporterInfoCache exporterInfoCache;
    private final ExporterSupportDao exporterSupportDao;
    private final ExporterSupportCache exporterSupportCache;
    private final ExportFileInfoCrudOperation exportFileInfoCrudOperation;
    private final ExportFileInfoDao exportFileInfoDao;
    private final ExportTaskCrudOperation exportTaskCrudOperation;
    private final ExportTaskDao exportTaskDao;
    private final ExportTaskSettingCrudOperation exportTaskSettingCrudOperation;
    private final ExportTaskSettingDao exportTaskSettingDao;
    private final ExportTemplateInfoCrudOperation exportTemplateInfoCrudOperation;
    private final ExportTemplateInfoDao exportTemplateInfoDao;
    private final ImportConfDao importConfDao;
    private final ImportConfCache importConfCache;
    private final ImporterInfoDao importerInfoDao;
    private final ImporterInfoCache importerInfoCache;
    private final ImporterSupportDao importerSupportDao;
    private final ImporterSupportCache importerSupportCache;
    private final ImportFileInfoCrudOperation importFileInfoCrudOperation;
    private final ImportFileInfoDao importFileInfoDao;
    private final ImportTaskCrudOperation importTaskCrudOperation;
    private final ImportTaskDao importTaskDao;
    private final ImportTaskSettingCrudOperation importTaskSettingCrudOperation;
    private final ImportTaskSettingDao importTaskSettingDao;
    private final ImportTemplateInfoCrudOperation importTemplateInfoCrudOperation;
    private final ImportTemplateInfoDao importTemplateInfoDao;
    private final ReaderInfoDao readerInfoDao;
    private final ReaderInfoCache readerInfoCache;
    private final ReaderSupportDao readerSupportDao;
    private final ReaderSupportCache readerSupportCache;
    private final WriterInfoDao writerInfoDao;
    private final WriterInfoCache writerInfoCache;
    private final WriterSupportDao writerSupportDao;
    private final WriterSupportCache writerSupportCache;
    private final ExportMetadataDao exportMetadataDao;
    private final ExportMetadataCache exportMetadataCache;
    private final ImportMetadataDao importMetadataDao;
    private final ImportMetadataCache importMetadataCache;
    private final UserCrudOperation userCrudOperation;
    private final UserDao userDao;

    @Value("${com.dwarfeng.fileio.cache.timeout.entity.export_conf}")
    private long exportConfTimeout;
    @Value("${com.dwarfeng.fileio.cache.timeout.entity.exporter_info}")
    private long exporterInfoTimeout;
    @Value("${com.dwarfeng.fileio.cache.timeout.entity.exporter_support}")
    private long exporterSupportTimeout;
    @Value("${com.dwarfeng.fileio.cache.timeout.entity.import_conf}")
    private long importConfTimeout;
    @Value("${com.dwarfeng.fileio.cache.timeout.entity.importer_info}")
    private long importerInfoTimeout;
    @Value("${com.dwarfeng.fileio.cache.timeout.entity.importer_support}")
    private long importerSupportTimeout;
    @Value("${com.dwarfeng.fileio.cache.timeout.entity.reader_info}")
    private long readerInfoTimeout;
    @Value("${com.dwarfeng.fileio.cache.timeout.entity.reader_support}")
    private long readerSupportTimeout;
    @Value("${com.dwarfeng.fileio.cache.timeout.entity.writer_info}")
    private long writerInfoTimeout;
    @Value("${com.dwarfeng.fileio.cache.timeout.entity.writer_support}")
    private long writerSupportTimeout;
    @Value("${com.dwarfeng.fileio.cache.timeout.entity.export_metadata}")
    private long exportMetadataTimeout;
    @Value("${com.dwarfeng.fileio.cache.timeout.entity.import_metadata}")
    private long importMetadataTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            GenerateConfiguration generateConfiguration,
            ExportConfDao exportConfDao,
            ExportConfCache exportConfCache,
            ExporterInfoDao exporterInfoDao,
            ExporterInfoCache exporterInfoCache,
            ExporterSupportDao exporterSupportDao,
            ExporterSupportCache exporterSupportCache,
            ExportFileInfoCrudOperation exportFileInfoCrudOperation,
            ExportFileInfoDao exportFileInfoDao,
            ExportTaskCrudOperation exportTaskCrudOperation,
            ExportTaskDao exportTaskDao,
            ExportTaskSettingCrudOperation exportTaskSettingCrudOperation,
            ExportTaskSettingDao exportTaskSettingDao,
            ExportTemplateInfoCrudOperation exportTemplateInfoCrudOperation,
            ExportTemplateInfoDao exportTemplateInfoDao,
            ImportConfDao importConfDao,
            ImportConfCache importConfCache,
            ImporterInfoDao importerInfoDao,
            ImporterInfoCache importerInfoCache,
            ImporterSupportDao importerSupportDao,
            ImporterSupportCache importerSupportCache,
            ImportFileInfoCrudOperation importFileInfoCrudOperation,
            ImportFileInfoDao importFileInfoDao,
            ImportTaskCrudOperation importTaskCrudOperation,
            ImportTaskDao importTaskDao,
            ImportTaskSettingCrudOperation importTaskSettingCrudOperation,
            ImportTaskSettingDao importTaskSettingDao,
            ImportTemplateInfoCrudOperation importTemplateInfoCrudOperation,
            ImportTemplateInfoDao importTemplateInfoDao,
            ReaderInfoDao readerInfoDao,
            ReaderInfoCache readerInfoCache,
            ReaderSupportDao readerSupportDao,
            ReaderSupportCache readerSupportCache,
            WriterInfoDao writerInfoDao,
            WriterInfoCache writerInfoCache,
            WriterSupportDao writerSupportDao,
            WriterSupportCache writerSupportCache,
            ExportMetadataDao exportMetadataDao,
            ExportMetadataCache exportMetadataCache,
            ImportMetadataDao importMetadataDao,
            ImportMetadataCache importMetadataCache,
            UserCrudOperation userCrudOperation,
            UserDao userDao
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.generateConfiguration = generateConfiguration;
        this.exportConfDao = exportConfDao;
        this.exportConfCache = exportConfCache;
        this.exporterInfoDao = exporterInfoDao;
        this.exporterInfoCache = exporterInfoCache;
        this.exporterSupportDao = exporterSupportDao;
        this.exporterSupportCache = exporterSupportCache;
        this.exportFileInfoCrudOperation = exportFileInfoCrudOperation;
        this.exportFileInfoDao = exportFileInfoDao;
        this.exportTaskCrudOperation = exportTaskCrudOperation;
        this.exportTaskDao = exportTaskDao;
        this.exportTaskSettingCrudOperation = exportTaskSettingCrudOperation;
        this.exportTaskSettingDao = exportTaskSettingDao;
        this.exportTemplateInfoCrudOperation = exportTemplateInfoCrudOperation;
        this.exportTemplateInfoDao = exportTemplateInfoDao;
        this.importConfDao = importConfDao;
        this.importConfCache = importConfCache;
        this.importerInfoDao = importerInfoDao;
        this.importerInfoCache = importerInfoCache;
        this.importerSupportDao = importerSupportDao;
        this.importerSupportCache = importerSupportCache;
        this.importFileInfoCrudOperation = importFileInfoCrudOperation;
        this.importFileInfoDao = importFileInfoDao;
        this.importTaskCrudOperation = importTaskCrudOperation;
        this.importTaskDao = importTaskDao;
        this.importTaskSettingCrudOperation = importTaskSettingCrudOperation;
        this.importTaskSettingDao = importTaskSettingDao;
        this.importTemplateInfoCrudOperation = importTemplateInfoCrudOperation;
        this.importTemplateInfoDao = importTemplateInfoDao;
        this.readerInfoDao = readerInfoDao;
        this.readerInfoCache = readerInfoCache;
        this.readerSupportDao = readerSupportDao;
        this.readerSupportCache = readerSupportCache;
        this.writerInfoDao = writerInfoDao;
        this.writerInfoCache = writerInfoCache;
        this.writerSupportDao = writerSupportDao;
        this.writerSupportCache = writerSupportCache;
        this.exportMetadataDao = exportMetadataDao;
        this.exportMetadataCache = exportMetadataCache;
        this.importMetadataDao = importMetadataDao;
        this.importMetadataCache = importMetadataCache;
        this.userCrudOperation = userCrudOperation;
        this.userDao = userDao;
    }

    @Bean
    public GeneralBatchCrudService<TaskSettingItemKey, ExportConf> exportConfGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportConfDao,
                exportConfCache,
                new ExceptionKeyGenerator<>(),
                exportConfTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ExportConf> exportConfDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportConfDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ExportConf> exportConfDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportConfDao
        );
    }

    @Bean
    public GeneralBatchCrudService<TaskSettingItemKey, ExporterInfo> exporterInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exporterInfoDao,
                exporterInfoCache,
                new ExceptionKeyGenerator<>(),
                exporterInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ExporterInfo> exporterInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exporterInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ExporterInfo> exporterInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exporterInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, ExporterSupport> exporterSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exporterSupportDao,
                exporterSupportCache,
                new ExceptionKeyGenerator<>(),
                exporterSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ExporterSupport> exporterSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exporterSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ExporterSupport> exporterSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exporterSupportDao
        );
    }

    @Bean
    public CustomBatchCrudService<TaskItemKey, ExportFileInfo> exportFileInfoCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportFileInfoCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ExportFileInfo> exportFileInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportFileInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ExportFileInfo> exportFileInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportFileInfoDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, ExportTask> exportTaskCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportTaskCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ExportTask> exportTaskDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportTaskDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ExportTask> exportTaskDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportTaskDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, ExportTaskSetting> exportTaskSettingCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportTaskSettingCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ExportTaskSetting> exportTaskSettingDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportTaskSettingDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ExportTaskSetting> exportTaskSettingDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportTaskSettingDao
        );
    }

    @Bean
    public CustomBatchCrudService<TaskSettingItemKey, ExportTemplateInfo> exportTemplateInfoCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportTemplateInfoCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ExportTemplateInfo> exportTemplateInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportTemplateInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ExportTemplateInfo> exportTemplateInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportTemplateInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<TaskSettingItemKey, ImportConf> importConfGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importConfDao,
                importConfCache,
                new ExceptionKeyGenerator<>(),
                importConfTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ImportConf> importConfDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importConfDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ImportConf> importConfDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importConfDao
        );
    }

    @Bean
    public GeneralBatchCrudService<TaskSettingItemKey, ImporterInfo> importerInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importerInfoDao,
                importerInfoCache,
                new ExceptionKeyGenerator<>(),
                importerInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ImporterInfo> importerInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importerInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ImporterInfo> importerInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importerInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, ImporterSupport> importerSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importerSupportDao,
                importerSupportCache,
                new ExceptionKeyGenerator<>(),
                importerSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ImporterSupport> importerSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importerSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ImporterSupport> importerSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importerSupportDao
        );
    }

    @Bean
    public CustomBatchCrudService<TaskItemKey, ImportFileInfo> importFileInfoCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importFileInfoCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ImportFileInfo> importFileInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importFileInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ImportFileInfo> importFileInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importFileInfoDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, ImportTask> importTaskCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importTaskCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ImportTask> importTaskDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importTaskDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ImportTask> importTaskDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importTaskDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, ImportTaskSetting> importTaskSettingCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importTaskSettingCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ImportTaskSetting> importTaskSettingDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importTaskSettingDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ImportTaskSetting> importTaskSettingDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importTaskSettingDao
        );
    }

    @Bean
    public CustomBatchCrudService<TaskSettingItemKey, ImportTemplateInfo> importTemplateInfoCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importTemplateInfoCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ImportTemplateInfo> importTemplateInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importTemplateInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ImportTemplateInfo> importTemplateInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importTemplateInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<TaskSettingItemKey, ReaderInfo> readerInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                readerInfoDao,
                readerInfoCache,
                new ExceptionKeyGenerator<>(),
                readerInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ReaderInfo> readerInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                readerInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ReaderInfo> readerInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                readerInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, ReaderSupport> readerSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                readerSupportDao,
                readerSupportCache,
                new ExceptionKeyGenerator<>(),
                readerSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ReaderSupport> readerSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                readerSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ReaderSupport> readerSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                readerSupportDao
        );
    }

    @Bean
    public GeneralBatchCrudService<TaskSettingItemKey, WriterInfo> writerInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                writerInfoDao,
                writerInfoCache,
                new ExceptionKeyGenerator<>(),
                writerInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<WriterInfo> writerInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                writerInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<WriterInfo> writerInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                writerInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, WriterSupport> writerSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                writerSupportDao,
                writerSupportCache,
                new ExceptionKeyGenerator<>(),
                writerSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<WriterSupport> writerSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                writerSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<WriterSupport> writerSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                writerSupportDao
        );
    }

    @Bean
    public GeneralBatchCrudService<TaskItemKey, ExportMetadata> exportMetadataGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportMetadataDao,
                exportMetadataCache,
                new ExceptionKeyGenerator<>(),
                exportMetadataTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ExportMetadata> exportMetadataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportMetadataDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ExportMetadata> exportMetadataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                exportMetadataDao
        );
    }

    @Bean
    public GeneralBatchCrudService<TaskItemKey, ImportMetadata> importMetadataGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importMetadataDao,
                importMetadataCache,
                new ExceptionKeyGenerator<>(),
                importMetadataTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ImportMetadata> importMetadataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importMetadataDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ImportMetadata> importMetadataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                importMetadataDao
        );
    }

    @Bean
    public CustomBatchCrudService<StringIdKey, User> userCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                userCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<User> userDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                userDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<User> userDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                userDao
        );
    }
}
