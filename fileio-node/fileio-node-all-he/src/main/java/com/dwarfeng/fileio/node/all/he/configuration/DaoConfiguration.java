package com.dwarfeng.fileio.node.all.he.configuration;

import com.dwarfeng.fileio.impl.bean.BeanMapper;
import com.dwarfeng.fileio.impl.bean.entity.*;
import com.dwarfeng.fileio.impl.bean.key.HibernateTaskItemKey;
import com.dwarfeng.fileio.impl.bean.key.HibernateTaskSettingItemKey;
import com.dwarfeng.fileio.impl.dao.preset.*;
import com.dwarfeng.fileio.stack.bean.entity.*;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DefaultDeletionMod;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    private final HibernateTemplate hibernateTemplate;

    private final ExportConfPresetCriteriaMaker exportConfPresetCriteriaMaker;
    private final ExporterInfoPresetCriteriaMaker exporterInfoPresetCriteriaMaker;
    private final ExporterSupportPresetCriteriaMaker exporterSupportPresetCriteriaMaker;
    private final ExportFileInfoPresetCriteriaMaker exportFileInfoPresetCriteriaMaker;
    private final ExportTaskPresetCriteriaMaker exportTaskPresetCriteriaMaker;
    private final ExportTaskSettingPresetCriteriaMaker exportTaskSettingPresetCriteriaMaker;
    private final ExportTemplateInfoPresetCriteriaMaker exportTemplateInfoPresetCriteriaMaker;
    private final ImportConfPresetCriteriaMaker importConfPresetCriteriaMaker;
    private final ImporterInfoPresetCriteriaMaker importerInfoPresetCriteriaMaker;
    private final ImporterSupportPresetCriteriaMaker importerSupportPresetCriteriaMaker;
    private final ImportFileInfoPresetCriteriaMaker importFileInfoPresetCriteriaMaker;
    private final ImportTaskPresetCriteriaMaker importTaskPresetCriteriaMaker;
    private final ImportTaskSettingPresetCriteriaMaker importTaskSettingPresetCriteriaMaker;
    private final ImportTemplateInfoPresetCriteriaMaker importTemplateInfoPresetCriteriaMaker;
    private final ReaderInfoPresetCriteriaMaker readerInfoPresetCriteriaMaker;
    private final ReaderSupportPresetCriteriaMaker readerSupportPresetCriteriaMaker;
    private final WriterInfoPresetCriteriaMaker writerInfoPresetCriteriaMaker;
    private final WriterSupportPresetCriteriaMaker writerSupportPresetCriteriaMaker;
    private final ExportMetadataPresetCriteriaMaker exportMetadataPresetCriteriaMaker;
    private final ImportMetadataPresetCriteriaMaker importMetadataPresetCriteriaMaker;
    private final UserPresetCriteriaMaker userPresetCriteriaMaker;

    @Value("${com.dwarfeng.fileio.hibernate.jdbc.batch_size}")
    private int batchSize;

    public DaoConfiguration(
            HibernateTemplate hibernateTemplate,
            ExportConfPresetCriteriaMaker exportConfPresetCriteriaMaker,
            ExporterInfoPresetCriteriaMaker exporterInfoPresetCriteriaMaker,
            ExporterSupportPresetCriteriaMaker exporterSupportPresetCriteriaMaker,
            ExportFileInfoPresetCriteriaMaker exportFileInfoPresetCriteriaMaker,
            ExportTaskPresetCriteriaMaker exportTaskPresetCriteriaMaker,
            ExportTaskSettingPresetCriteriaMaker exportTaskSettingPresetCriteriaMaker,
            ExportTemplateInfoPresetCriteriaMaker exportTemplateInfoPresetCriteriaMaker,
            ImportConfPresetCriteriaMaker importConfPresetCriteriaMaker,
            ImporterInfoPresetCriteriaMaker importerInfoPresetCriteriaMaker,
            ImporterSupportPresetCriteriaMaker importerSupportPresetCriteriaMaker,
            ImportFileInfoPresetCriteriaMaker importFileInfoPresetCriteriaMaker,
            ImportTaskPresetCriteriaMaker importTaskPresetCriteriaMaker,
            ImportTaskSettingPresetCriteriaMaker importTaskSettingPresetCriteriaMaker,
            ImportTemplateInfoPresetCriteriaMaker importTemplateInfoPresetCriteriaMaker,
            ReaderInfoPresetCriteriaMaker readerInfoPresetCriteriaMaker,
            ReaderSupportPresetCriteriaMaker readerSupportPresetCriteriaMaker,
            WriterInfoPresetCriteriaMaker writerInfoPresetCriteriaMaker,
            WriterSupportPresetCriteriaMaker writerSupportPresetCriteriaMaker,
            ExportMetadataPresetCriteriaMaker exportMetadataPresetCriteriaMaker,
            ImportMetadataPresetCriteriaMaker importMetadataPresetCriteriaMaker,
            UserPresetCriteriaMaker userPresetCriteriaMaker
    ) {
        this.hibernateTemplate = hibernateTemplate;
        this.exportConfPresetCriteriaMaker = exportConfPresetCriteriaMaker;
        this.exporterInfoPresetCriteriaMaker = exporterInfoPresetCriteriaMaker;
        this.exporterSupportPresetCriteriaMaker = exporterSupportPresetCriteriaMaker;
        this.exportFileInfoPresetCriteriaMaker = exportFileInfoPresetCriteriaMaker;
        this.exportTaskPresetCriteriaMaker = exportTaskPresetCriteriaMaker;
        this.exportTaskSettingPresetCriteriaMaker = exportTaskSettingPresetCriteriaMaker;
        this.exportTemplateInfoPresetCriteriaMaker = exportTemplateInfoPresetCriteriaMaker;
        this.importConfPresetCriteriaMaker = importConfPresetCriteriaMaker;
        this.importerInfoPresetCriteriaMaker = importerInfoPresetCriteriaMaker;
        this.importerSupportPresetCriteriaMaker = importerSupportPresetCriteriaMaker;
        this.importFileInfoPresetCriteriaMaker = importFileInfoPresetCriteriaMaker;
        this.importTaskPresetCriteriaMaker = importTaskPresetCriteriaMaker;
        this.importTaskSettingPresetCriteriaMaker = importTaskSettingPresetCriteriaMaker;
        this.importTemplateInfoPresetCriteriaMaker = importTemplateInfoPresetCriteriaMaker;
        this.readerInfoPresetCriteriaMaker = readerInfoPresetCriteriaMaker;
        this.readerSupportPresetCriteriaMaker = readerSupportPresetCriteriaMaker;
        this.writerInfoPresetCriteriaMaker = writerInfoPresetCriteriaMaker;
        this.writerSupportPresetCriteriaMaker = writerSupportPresetCriteriaMaker;
        this.exportMetadataPresetCriteriaMaker = exportMetadataPresetCriteriaMaker;
        this.importMetadataPresetCriteriaMaker = importMetadataPresetCriteriaMaker;
        this.userPresetCriteriaMaker = userPresetCriteriaMaker;
    }

    @Bean
    public HibernateBatchBaseDao<TaskSettingItemKey, HibernateTaskSettingItemKey, ExportConf, HibernateExportConf>
    exportConfHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TaskSettingItemKey.class, HibernateTaskSettingItemKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(ExportConf.class, HibernateExportConf.class, BeanMapper.class),
                HibernateExportConf.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ExportConf, HibernateExportConf> exportConfHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ExportConf.class, HibernateExportConf.class, BeanMapper.class),
                HibernateExportConf.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ExportConf, HibernateExportConf> exportConfHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ExportConf.class, HibernateExportConf.class, BeanMapper.class),
                HibernateExportConf.class,
                exportConfPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskSettingItemKey, HibernateTaskSettingItemKey, ExporterInfo, HibernateExporterInfo>
    exporterInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TaskSettingItemKey.class, HibernateTaskSettingItemKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(ExporterInfo.class, HibernateExporterInfo.class, BeanMapper.class),
                HibernateExporterInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ExporterInfo, HibernateExporterInfo> exporterInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ExporterInfo.class, HibernateExporterInfo.class, BeanMapper.class),
                HibernateExporterInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ExporterInfo, HibernateExporterInfo> exporterInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ExporterInfo.class, HibernateExporterInfo.class, BeanMapper.class),
                HibernateExporterInfo.class,
                exporterInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, ExporterSupport, HibernateExporterSupport>
    exporterSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        ExporterSupport.class, HibernateExporterSupport.class, BeanMapper.class
                ),
                HibernateExporterSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ExporterSupport, HibernateExporterSupport>
    exporterSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ExporterSupport.class, HibernateExporterSupport.class, BeanMapper.class
                ),
                HibernateExporterSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ExporterSupport, HibernateExporterSupport>
    exporterSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ExporterSupport.class, HibernateExporterSupport.class, BeanMapper.class
                ),
                HibernateExporterSupport.class,
                exporterSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskItemKey, HibernateTaskItemKey, ExportFileInfo, HibernateExportFileInfo>
    exportFileInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(TaskItemKey.class, HibernateTaskItemKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        ExportFileInfo.class, HibernateExportFileInfo.class, BeanMapper.class
                ),
                HibernateExportFileInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ExportFileInfo, HibernateExportFileInfo> exportFileInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ExportFileInfo.class, HibernateExportFileInfo.class, BeanMapper.class
                ),
                HibernateExportFileInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ExportFileInfo, HibernateExportFileInfo> exportFileInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ExportFileInfo.class, HibernateExportFileInfo.class, BeanMapper.class
                ),
                HibernateExportFileInfo.class,
                exportFileInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, ExportTask, HibernateExportTask>
    exportTaskHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(ExportTask.class, HibernateExportTask.class, BeanMapper.class),
                HibernateExportTask.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ExportTask, HibernateExportTask> exportTaskHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ExportTask.class, HibernateExportTask.class, BeanMapper.class),
                HibernateExportTask.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ExportTask, HibernateExportTask> exportTaskHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ExportTask.class, HibernateExportTask.class, BeanMapper.class),
                HibernateExportTask.class,
                exportTaskPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, ExportTaskSetting, HibernateExportTaskSetting>
    exportTaskSettingHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        ExportTaskSetting.class, HibernateExportTaskSetting.class, BeanMapper.class
                ),
                HibernateExportTaskSetting.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ExportTaskSetting, HibernateExportTaskSetting>
    exportTaskSettingHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ExportTaskSetting.class, HibernateExportTaskSetting.class, BeanMapper.class
                ),
                HibernateExportTaskSetting.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ExportTaskSetting, HibernateExportTaskSetting>
    exportTaskSettingHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ExportTaskSetting.class, HibernateExportTaskSetting.class, BeanMapper.class
                ),
                HibernateExportTaskSetting.class,
                exportTaskSettingPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskSettingItemKey, HibernateTaskSettingItemKey, ExportTemplateInfo,
            HibernateExportTemplateInfo> exportTemplateInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TaskSettingItemKey.class, HibernateTaskSettingItemKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(
                        ExportTemplateInfo.class, HibernateExportTemplateInfo.class, BeanMapper.class
                ),
                HibernateExportTemplateInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ExportTemplateInfo, HibernateExportTemplateInfo>
    exportTemplateInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ExportTemplateInfo.class, HibernateExportTemplateInfo.class, BeanMapper.class
                ),
                HibernateExportTemplateInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ExportTemplateInfo, HibernateExportTemplateInfo>
    exportTemplateInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ExportTemplateInfo.class, HibernateExportTemplateInfo.class, BeanMapper.class
                ),
                HibernateExportTemplateInfo.class,
                exportTemplateInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskSettingItemKey, HibernateTaskSettingItemKey, ImportConf, HibernateImportConf>
    importConfHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TaskSettingItemKey.class, HibernateTaskSettingItemKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(ImportConf.class, HibernateImportConf.class, BeanMapper.class),
                HibernateImportConf.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ImportConf, HibernateImportConf> importConfHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ImportConf.class, HibernateImportConf.class, BeanMapper.class),
                HibernateImportConf.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ImportConf, HibernateImportConf> importConfHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ImportConf.class, HibernateImportConf.class, BeanMapper.class),
                HibernateImportConf.class,
                importConfPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskSettingItemKey, HibernateTaskSettingItemKey, ImporterInfo, HibernateImporterInfo>
    importerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TaskSettingItemKey.class, HibernateTaskSettingItemKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(ImporterInfo.class, HibernateImporterInfo.class, BeanMapper.class),
                HibernateImporterInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ImporterInfo, HibernateImporterInfo> importerInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ImporterInfo.class, HibernateImporterInfo.class, BeanMapper.class),
                HibernateImporterInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ImporterInfo, HibernateImporterInfo> importerInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ImporterInfo.class, HibernateImporterInfo.class, BeanMapper.class),
                HibernateImporterInfo.class,
                importerInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, ImporterSupport, HibernateImporterSupport>
    importerSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(
                        ImporterSupport.class, HibernateImporterSupport.class, BeanMapper.class
                ),
                HibernateImporterSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ImporterSupport, HibernateImporterSupport>
    importerSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ImporterSupport.class, HibernateImporterSupport.class, BeanMapper.class
                ),
                HibernateImporterSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ImporterSupport, HibernateImporterSupport>
    importerSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ImporterSupport.class, HibernateImporterSupport.class, BeanMapper.class
                ),
                HibernateImporterSupport.class,
                importerSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskItemKey, HibernateTaskItemKey, ImportFileInfo, HibernateImportFileInfo>
    importFileInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(TaskItemKey.class, HibernateTaskItemKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        ImportFileInfo.class, HibernateImportFileInfo.class, BeanMapper.class
                ),
                HibernateImportFileInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ImportFileInfo, HibernateImportFileInfo> importFileInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ImportFileInfo.class, HibernateImportFileInfo.class, BeanMapper.class
                ),
                HibernateImportFileInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ImportFileInfo, HibernateImportFileInfo> importFileInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ImportFileInfo.class, HibernateImportFileInfo.class, BeanMapper.class
                ),
                HibernateImportFileInfo.class,
                importFileInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, ImportTask, HibernateImportTask>
    importTaskHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(ImportTask.class, HibernateImportTask.class, BeanMapper.class),
                HibernateImportTask.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ImportTask, HibernateImportTask> importTaskHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ImportTask.class, HibernateImportTask.class, BeanMapper.class),
                HibernateImportTask.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ImportTask, HibernateImportTask> importTaskHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ImportTask.class, HibernateImportTask.class, BeanMapper.class),
                HibernateImportTask.class,
                importTaskPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, ImportTaskSetting, HibernateImportTaskSetting>
    importTaskSettingHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        ImportTaskSetting.class, HibernateImportTaskSetting.class, BeanMapper.class
                ),
                HibernateImportTaskSetting.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ImportTaskSetting, HibernateImportTaskSetting>
    importTaskSettingHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ImportTaskSetting.class, HibernateImportTaskSetting.class, BeanMapper.class
                ),
                HibernateImportTaskSetting.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ImportTaskSetting, HibernateImportTaskSetting>
    importTaskSettingHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ImportTaskSetting.class, HibernateImportTaskSetting.class, BeanMapper.class
                ),
                HibernateImportTaskSetting.class,
                importTaskSettingPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskSettingItemKey, HibernateTaskSettingItemKey, ImportTemplateInfo,
            HibernateImportTemplateInfo> importTemplateInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TaskSettingItemKey.class, HibernateTaskSettingItemKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(
                        ImportTemplateInfo.class, HibernateImportTemplateInfo.class, BeanMapper.class
                ),
                HibernateImportTemplateInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ImportTemplateInfo, HibernateImportTemplateInfo>
    importTemplateInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ImportTemplateInfo.class, HibernateImportTemplateInfo.class, BeanMapper.class
                ),
                HibernateImportTemplateInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ImportTemplateInfo, HibernateImportTemplateInfo>
    importTemplateInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ImportTemplateInfo.class, HibernateImportTemplateInfo.class, BeanMapper.class
                ),
                HibernateImportTemplateInfo.class,
                importTemplateInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskSettingItemKey, HibernateTaskSettingItemKey, ReaderInfo, HibernateReaderInfo>
    readerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TaskSettingItemKey.class, HibernateTaskSettingItemKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(ReaderInfo.class, HibernateReaderInfo.class, BeanMapper.class),
                HibernateReaderInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ReaderInfo, HibernateReaderInfo> readerInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ReaderInfo.class, HibernateReaderInfo.class, BeanMapper.class),
                HibernateReaderInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ReaderInfo, HibernateReaderInfo> readerInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ReaderInfo.class, HibernateReaderInfo.class, BeanMapper.class),
                HibernateReaderInfo.class,
                readerInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, ReaderSupport, HibernateReaderSupport>
    readerSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        ReaderSupport.class, HibernateReaderSupport.class, BeanMapper.class
                ),
                HibernateReaderSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ReaderSupport, HibernateReaderSupport> readerSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ReaderSupport.class, HibernateReaderSupport.class, BeanMapper.class
                ),
                HibernateReaderSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ReaderSupport, HibernateReaderSupport> readerSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ReaderSupport.class, HibernateReaderSupport.class, BeanMapper.class
                ),
                HibernateReaderSupport.class,
                readerSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskSettingItemKey, HibernateTaskSettingItemKey, WriterInfo, HibernateWriterInfo>
    writerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TaskSettingItemKey.class, HibernateTaskSettingItemKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(WriterInfo.class, HibernateWriterInfo.class, BeanMapper.class),
                HibernateWriterInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<WriterInfo, HibernateWriterInfo> writerInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(WriterInfo.class, HibernateWriterInfo.class, BeanMapper.class),
                HibernateWriterInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<WriterInfo, HibernateWriterInfo> writerInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(WriterInfo.class, HibernateWriterInfo.class, BeanMapper.class),
                HibernateWriterInfo.class,
                writerInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, WriterSupport, HibernateWriterSupport>
    writerSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        WriterSupport.class, HibernateWriterSupport.class, BeanMapper.class
                ),
                HibernateWriterSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<WriterSupport, HibernateWriterSupport> writerSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        WriterSupport.class, HibernateWriterSupport.class, BeanMapper.class
                ),
                HibernateWriterSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<WriterSupport, HibernateWriterSupport> writerSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        WriterSupport.class, HibernateWriterSupport.class, BeanMapper.class
                ),
                HibernateWriterSupport.class,
                writerSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskItemKey, HibernateTaskItemKey, ExportMetadata, HibernateExportMetadata>
    exportMetadataHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TaskItemKey.class, HibernateTaskItemKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(
                        ExportMetadata.class, HibernateExportMetadata.class, BeanMapper.class
                ),
                HibernateExportMetadata.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ExportMetadata, HibernateExportMetadata> exportMetadataHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ExportMetadata.class, HibernateExportMetadata.class, BeanMapper.class
                ),
                HibernateExportMetadata.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ExportMetadata, HibernateExportMetadata> exportMetadataHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ExportMetadata.class, HibernateExportMetadata.class, BeanMapper.class
                ),
                HibernateExportMetadata.class,
                exportMetadataPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskItemKey, HibernateTaskItemKey, ImportMetadata, HibernateImportMetadata>
    importMetadataHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TaskItemKey.class, HibernateTaskItemKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(
                        ImportMetadata.class, HibernateImportMetadata.class, BeanMapper.class
                ),
                HibernateImportMetadata.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ImportMetadata, HibernateImportMetadata> importMetadataHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ImportMetadata.class, HibernateImportMetadata.class, BeanMapper.class
                ),
                HibernateImportMetadata.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ImportMetadata, HibernateImportMetadata> importMetadataHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ImportMetadata.class, HibernateImportMetadata.class, BeanMapper.class
                ),
                HibernateImportMetadata.class,
                importMetadataPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, User, HibernateUser> userHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(User.class, HibernateUser.class, BeanMapper.class),
                HibernateUser.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<User, HibernateUser> userHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(User.class, HibernateUser.class, BeanMapper.class),
                HibernateUser.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<User, HibernateUser> userHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(User.class, HibernateUser.class, BeanMapper.class),
                HibernateUser.class,
                userPresetCriteriaMaker
        );
    }
}
