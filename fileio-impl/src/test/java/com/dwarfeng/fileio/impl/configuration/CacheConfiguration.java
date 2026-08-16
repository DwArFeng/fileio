package com.dwarfeng.fileio.impl.configuration;

import com.dwarfeng.fileio.sdk.bean.BeanMapper;
import com.dwarfeng.fileio.sdk.bean.entity.*;
import com.dwarfeng.fileio.sdk.bean.key.formatter.TaskItemStringKeyFormatter;
import com.dwarfeng.fileio.sdk.bean.key.formatter.TaskSettingItemStringKeyFormatter;
import com.dwarfeng.fileio.stack.bean.entity.*;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    private final RedisTemplate<String, ?> template;

    @Value("${com.dwarfeng.fileio.cache.prefix.entity.export_conf}")
    private String exportConfPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.exporter_info}")
    private String exporterInfoPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.exporter_support}")
    private String exporterSupportPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.export_file_info}")
    private String exportFileInfoPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.export_task}")
    private String exportTaskPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.export_task_setting}")
    private String exportTaskSettingPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.export_template_info}")
    private String exportTemplateInfoPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.import_conf}")
    private String importConfPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.importer_info}")
    private String importerInfoPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.importer_support}")
    private String importerSupportPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.import_file_info}")
    private String importFileInfoPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.import_task}")
    private String importTaskPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.import_task_setting}")
    private String importTaskSettingPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.import_template_info}")
    private String importTemplateInfoPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.reader_info}")
    private String readerInfoPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.reader_support}")
    private String readerSupportPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.writer_info}")
    private String writerInfoPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.writer_support}")
    private String writerSupportPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.export_metadata}")
    private String exportMetadataPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.import_metadata}")
    private String importMetadataPrefix;
    @Value("${com.dwarfeng.fileio.cache.prefix.entity.user}")
    private String userPrefix;

    public CacheConfiguration(RedisTemplate<String, ?> template) {
        this.template = template;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskSettingItemKey, ExportConf, FastJsonExportConf> exportConfRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonExportConf>) template,
                new TaskSettingItemStringKeyFormatter(exportConfPrefix),
                new MapStructBeanTransformer<>(ExportConf.class, FastJsonExportConf.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskSettingItemKey, ExporterInfo, FastJsonExporterInfo>
    exporterInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonExporterInfo>) template,
                new TaskSettingItemStringKeyFormatter(exporterInfoPrefix),
                new MapStructBeanTransformer<>(ExporterInfo.class, FastJsonExporterInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, ExporterSupport, FastJsonExporterSupport>
    exporterSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonExporterSupport>) template,
                new StringIdStringKeyFormatter(exporterSupportPrefix),
                new MapStructBeanTransformer<>(
                        ExporterSupport.class, FastJsonExporterSupport.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskItemKey, ExportFileInfo, FastJsonExportFileInfo>
    exportFileInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonExportFileInfo>) template,
                new TaskItemStringKeyFormatter(exportFileInfoPrefix),
                new MapStructBeanTransformer<>(ExportFileInfo.class, FastJsonExportFileInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, ExportTask, FastJsonExportTask> exportTaskRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonExportTask>) template,
                new LongIdStringKeyFormatter(exportTaskPrefix),
                new MapStructBeanTransformer<>(ExportTask.class, FastJsonExportTask.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, ExportTaskSetting, FastJsonExportTaskSetting>
    exportTaskSettingRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonExportTaskSetting>) template,
                new LongIdStringKeyFormatter(exportTaskSettingPrefix),
                new MapStructBeanTransformer<>(
                        ExportTaskSetting.class, FastJsonExportTaskSetting.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskSettingItemKey, ExportTemplateInfo, FastJsonExportTemplateInfo>
    exportTemplateInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonExportTemplateInfo>) template,
                new TaskSettingItemStringKeyFormatter(exportTemplateInfoPrefix),
                new MapStructBeanTransformer<>(
                        ExportTemplateInfo.class, FastJsonExportTemplateInfo.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskSettingItemKey, ImportConf, FastJsonImportConf> importConfRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonImportConf>) template,
                new TaskSettingItemStringKeyFormatter(importConfPrefix),
                new MapStructBeanTransformer<>(ImportConf.class, FastJsonImportConf.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskSettingItemKey, ImporterInfo, FastJsonImporterInfo>
    importerInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonImporterInfo>) template,
                new TaskSettingItemStringKeyFormatter(importerInfoPrefix),
                new MapStructBeanTransformer<>(ImporterInfo.class, FastJsonImporterInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, ImporterSupport, FastJsonImporterSupport>
    importerSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonImporterSupport>) template,
                new StringIdStringKeyFormatter(importerSupportPrefix),
                new MapStructBeanTransformer<>(
                        ImporterSupport.class, FastJsonImporterSupport.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskItemKey, ImportFileInfo, FastJsonImportFileInfo>
    importFileInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonImportFileInfo>) template,
                new TaskItemStringKeyFormatter(importFileInfoPrefix),
                new MapStructBeanTransformer<>(ImportFileInfo.class, FastJsonImportFileInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, ImportTask, FastJsonImportTask> importTaskRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonImportTask>) template,
                new LongIdStringKeyFormatter(importTaskPrefix),
                new MapStructBeanTransformer<>(ImportTask.class, FastJsonImportTask.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, ImportTaskSetting, FastJsonImportTaskSetting>
    importTaskSettingRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonImportTaskSetting>) template,
                new LongIdStringKeyFormatter(importTaskSettingPrefix),
                new MapStructBeanTransformer<>(
                        ImportTaskSetting.class, FastJsonImportTaskSetting.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskSettingItemKey, ImportTemplateInfo, FastJsonImportTemplateInfo>
    importTemplateInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonImportTemplateInfo>) template,
                new TaskSettingItemStringKeyFormatter(importTemplateInfoPrefix),
                new MapStructBeanTransformer<>(
                        ImportTemplateInfo.class, FastJsonImportTemplateInfo.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskSettingItemKey, ReaderInfo, FastJsonReaderInfo> readerInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonReaderInfo>) template,
                new TaskSettingItemStringKeyFormatter(readerInfoPrefix),
                new MapStructBeanTransformer<>(ReaderInfo.class, FastJsonReaderInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, ReaderSupport, FastJsonReaderSupport> readerSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonReaderSupport>) template,
                new StringIdStringKeyFormatter(readerSupportPrefix),
                new MapStructBeanTransformer<>(ReaderSupport.class, FastJsonReaderSupport.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskSettingItemKey, WriterInfo, FastJsonWriterInfo> writerInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonWriterInfo>) template,
                new TaskSettingItemStringKeyFormatter(writerInfoPrefix),
                new MapStructBeanTransformer<>(WriterInfo.class, FastJsonWriterInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, WriterSupport, FastJsonWriterSupport> writerSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonWriterSupport>) template,
                new StringIdStringKeyFormatter(writerSupportPrefix),
                new MapStructBeanTransformer<>(WriterSupport.class, FastJsonWriterSupport.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskItemKey, ExportMetadata, FastJsonExportMetadata>
    exportMetadataRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonExportMetadata>) template,
                new TaskItemStringKeyFormatter(exportMetadataPrefix),
                new MapStructBeanTransformer<>(ExportMetadata.class, FastJsonExportMetadata.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskItemKey, ImportMetadata, FastJsonImportMetadata>
    importMetadataRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonImportMetadata>) template,
                new TaskItemStringKeyFormatter(importMetadataPrefix),
                new MapStructBeanTransformer<>(ImportMetadata.class, FastJsonImportMetadata.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, User, FastJsonUser> userRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonUser>) template,
                new StringIdStringKeyFormatter(userPrefix),
                new MapStructBeanTransformer<>(User.class, FastJsonUser.class, BeanMapper.class)
        );
    }
}
