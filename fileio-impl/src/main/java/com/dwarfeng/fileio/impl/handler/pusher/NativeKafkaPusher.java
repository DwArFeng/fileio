package com.dwarfeng.fileio.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.fileio.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地 Kafka 推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public
class NativeKafkaPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "kafka.native";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${com.dwarfeng.fileio.pusher.kafka.native.topic.export_reset}")
    private String exportResetTopic;
    @Value("${com.dwarfeng.fileio.pusher.kafka.native.topic.import_reset}")
    private String importResetTopic;
    @Value("${com.dwarfeng.fileio.pusher.kafka.native.topic.export_task_finished}")
    private String exportTaskFinishedTopic;
    @Value("${com.dwarfeng.fileio.pusher.kafka.native.topic.export_task_failed}")
    private String exportTaskFailedTopic;
    @Value("${com.dwarfeng.fileio.pusher.kafka.native.topic.export_task_expired}")
    private String exportTaskExpiredTopic;
    @Value("${com.dwarfeng.fileio.pusher.kafka.native.topic.export_task_dead}")
    private String exportTaskDeadTopic;
    @Value("${com.dwarfeng.fileio.pusher.kafka.native.topic.import_task_finished}")
    private String importTaskFinishedTopic;
    @Value("${com.dwarfeng.fileio.pusher.kafka.native.topic.import_task_failed}")
    private String importTaskFailedTopic;
    @Value("${com.dwarfeng.fileio.pusher.kafka.native.topic.import_task_expired}")
    private String importTaskExpiredTopic;
    @Value("${com.dwarfeng.fileio.pusher.kafka.native.topic.import_task_dead}")
    private String importTaskDeadTopic;
    @Value("${com.dwarfeng.fileio.pusher.kafka.native.topic.purge_finished}")
    private String purgeFinishedTopic;

    public NativeKafkaPusher(
            @Qualifier("nativeKafkaPusher.kafkaTemplate") KafkaTemplate<String, String> kafkaTemplate
    ) {
        super(PUSHER_TYPE);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void exportReset() {
        kafkaTemplate.send(exportResetTopic, StringUtils.EMPTY);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void importReset() {
        kafkaTemplate.send(importResetTopic, StringUtils.EMPTY);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void exportTaskFinished(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
        ExportTaskMessage message = new ExportTaskMessage(exportTaskSetting, exportTask);
        kafkaTemplate.send(exportTaskFinishedTopic, JSON.toJSONString(message));
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void exportTaskFailed(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
        ExportTaskMessage message = new ExportTaskMessage(exportTaskSetting, exportTask);
        kafkaTemplate.send(exportTaskFailedTopic, JSON.toJSONString(message));
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void exportTaskExpired(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
        ExportTaskMessage message = new ExportTaskMessage(exportTaskSetting, exportTask);
        kafkaTemplate.send(exportTaskExpiredTopic, JSON.toJSONString(message));
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void exportTaskDead(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
        ExportTaskMessage message = new ExportTaskMessage(exportTaskSetting, exportTask);
        kafkaTemplate.send(exportTaskDeadTopic, JSON.toJSONString(message));
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void importTaskFinished(ImportTaskSetting importTaskSetting, ImportTask importTask) {
        ImportTaskMessage message = new ImportTaskMessage(importTaskSetting, importTask);
        kafkaTemplate.send(importTaskFinishedTopic, JSON.toJSONString(message));
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void importTaskFailed(ImportTaskSetting importTaskSetting, ImportTask importTask) {
        ImportTaskMessage message = new ImportTaskMessage(importTaskSetting, importTask);
        kafkaTemplate.send(importTaskFailedTopic, JSON.toJSONString(message));
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void importTaskExpired(ImportTaskSetting importTaskSetting, ImportTask importTask) {
        ImportTaskMessage message = new ImportTaskMessage(importTaskSetting, importTask);
        kafkaTemplate.send(importTaskExpiredTopic, JSON.toJSONString(message));
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void importTaskDead(ImportTaskSetting importTaskSetting, ImportTask importTask) {
        ImportTaskMessage message = new ImportTaskMessage(importTaskSetting, importTask);
        kafkaTemplate.send(importTaskDeadTopic, JSON.toJSONString(message));
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void purgeFinished(PurgeFinishedResult purgeFinishedResult) {
        PurgeFinishedMessage message = new PurgeFinishedMessage(purgeFinishedResult);
        kafkaTemplate.send(purgeFinishedTopic, JSON.toJSONString(message));
    }

    @Override
    public String toString() {
        return "NativeKafkaPusher{" +
                "kafkaTemplate=" + kafkaTemplate +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }

    @Configuration
    public static class KafkaPusherConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPusherConfiguration.class);

        @Value("${com.dwarfeng.fileio.pusher.kafka.native.bootstrap_servers}")
        private String producerBootstrapServers;
        @Value("${com.dwarfeng.fileio.pusher.kafka.native.retries}")
        private int retries;
        @Value("${com.dwarfeng.fileio.pusher.kafka.native.linger}")
        private long linger;
        @Value("${com.dwarfeng.fileio.pusher.kafka.native.buffer_memory}")
        private long bufferMemory;
        @Value("${com.dwarfeng.fileio.pusher.kafka.native.batch_size}")
        private int batchSize;
        @Value("${com.dwarfeng.fileio.pusher.kafka.native.acks}")
        private String acks;
        @Value("${com.dwarfeng.fileio.pusher.kafka.native.transaction_prefix}")
        private String transactionPrefix;

        @Bean("nativeKafkaPusher.producerProperties")
        public Map<String, Object> producerProperties() {
            LOGGER.info("配置Kafka生产者属性...");
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapServers);
            props.put(ProducerConfig.RETRIES_CONFIG, retries);
            props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
            props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
            props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
            props.put(ProducerConfig.ACKS_CONFIG, acks);
            LOGGER.debug("Kafka生产者属性配置完成...");
            return props;
        }

        @Bean("nativeKafkaPusher.producerFactory")
        public ProducerFactory<String, String> producerFactory() {
            LOGGER.debug("配置 Kafka 生产者工厂...");
            Map<String, Object> properties = producerProperties();
            DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(properties);
            factory.setTransactionIdPrefix(transactionPrefix);
            factory.setKeySerializer(new StringSerializer());
            factory.setValueSerializer(new StringSerializer());
            LOGGER.debug("Kafka 生产者工厂配置完成");
            return factory;
        }

        @Bean("nativeKafkaPusher.kafkaTemplate")
        public KafkaTemplate<String, String> kafkaTemplate() {
            LOGGER.debug("生成 KafkaTemplate...");
            ProducerFactory<String, String> producerFactory = producerFactory();
            KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory, true);
            LOGGER.debug("KafkaTemplate 生成完成...");
            return kafkaTemplate;
        }

        @Bean("nativeKafkaPusher.kafkaTransactionManager")
        public KafkaTransactionManager<String, String> kafkaTransactionManager() {
            LOGGER.debug("生成 KafkaTransactionManager...");
            ProducerFactory<String, String> producerFactory = producerFactory();
            LOGGER.debug("KafkaTransactionManager 生成完成...");
            return new KafkaTransactionManager<>(producerFactory);
        }
    }

    /**
     * 导出任务消息。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    public static class ExportTaskMessage implements Dto {

        private static final long serialVersionUID = -5656874293206493469L;

        @JSONField(name = "export_task_setting", ordinal = 1)
        private ExportTaskSetting exportTaskSetting;

        @JSONField(name = "export_task", ordinal = 2)
        private ExportTask exportTask;

        public ExportTaskMessage() {
        }

        public ExportTaskMessage(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
            this.exportTaskSetting = exportTaskSetting;
            this.exportTask = exportTask;
        }

        public ExportTaskSetting getExportTaskSetting() {
            return exportTaskSetting;
        }

        public void setExportTaskSetting(ExportTaskSetting exportTaskSetting) {
            this.exportTaskSetting = exportTaskSetting;
        }

        public ExportTask getExportTask() {
            return exportTask;
        }

        public void setExportTask(ExportTask exportTask) {
            this.exportTask = exportTask;
        }

        @Override
        public String toString() {
            return "ExportTaskMessage{" +
                    "exportTaskSetting=" + exportTaskSetting +
                    ", exportTask=" + exportTask +
                    '}';
        }
    }

    /**
     * 导入任务消息。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    public static class ImportTaskMessage implements Dto {

        private static final long serialVersionUID = 4239491129177424492L;

        @JSONField(name = "import_task_setting", ordinal = 1)
        private ImportTaskSetting importTaskSetting;

        @JSONField(name = "import_task", ordinal = 2)
        private ImportTask importTask;

        public ImportTaskMessage() {
        }

        public ImportTaskMessage(ImportTaskSetting importTaskSetting, ImportTask importTask) {
            this.importTaskSetting = importTaskSetting;
            this.importTask = importTask;
        }

        public ImportTaskSetting getImportTaskSetting() {
            return importTaskSetting;
        }

        public void setImportTaskSetting(ImportTaskSetting importTaskSetting) {
            this.importTaskSetting = importTaskSetting;
        }

        public ImportTask getImportTask() {
            return importTask;
        }

        public void setImportTask(ImportTask importTask) {
            this.importTask = importTask;
        }

        @Override
        public String toString() {
            return "ImportTaskMessage{" +
                    "importTaskSetting=" + importTaskSetting +
                    ", importTask=" + importTask +
                    '}';
        }
    }

    /**
     * 清除完成消息。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    public static class PurgeFinishedMessage implements Dto {

        private static final long serialVersionUID = 4583804138953692719L;

        @JSONField(name = "purge_finished_result", ordinal = 1)
        private PurgeFinishedResult purgeFinishedResult;

        public PurgeFinishedMessage() {
        }

        public PurgeFinishedMessage(PurgeFinishedResult purgeFinishedResult) {
            this.purgeFinishedResult = purgeFinishedResult;
        }

        public PurgeFinishedResult getPurgeFinishedResult() {
            return purgeFinishedResult;
        }

        public void setPurgeFinishedResult(PurgeFinishedResult purgeFinishedResult) {
            this.purgeFinishedResult = purgeFinishedResult;
        }

        @Override
        public String toString() {
            return "PurgeFinishedMessage{" +
                    "purgeFinishedResult=" + purgeFinishedResult +
                    '}';
        }
    }
}
