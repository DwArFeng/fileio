package com.dwarfeng.fileio.impl.handler.exporter.csv;

import com.dwarfeng.fileio.sdk.handler.exporter.AbstractExecutor;
import com.dwarfeng.fileio.stack.bean.dto.TemporaryToFileCopyInfo;
import com.dwarfeng.fileio.stack.struct.ReadResult;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * CSV 导出器执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("csvExporterRegistry.csvExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CsvExecutor extends AbstractExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvExecutor.class);

    // 指定字符集为 UTF-8，以兼容不同操作系统。
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private final ApplicationContext ctx;

    private final Config config;

    private String temporaryFileIdentifier;

    private ValueSerializer valueSerializer;

    // 为了代码的可读性，此处不做简化。
    private String[] columnNames;
    private String[] fieldNames;

    private Writer temporaryFileWriter;
    private CSVPrinter csvPrinter;

    private boolean exceptionFlag = false;

    public CsvExecutor(ApplicationContext ctx, Config config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    public void beforeExecute() throws Exception {
        try {
            // 申请临时文件。
            temporaryFileIdentifier = UUID.randomUUID().toString();
            context.createTemporary(temporaryFileIdentifier);
            // 并打开临时文件的输出流。
            temporaryFileWriter = new OutputStreamWriter(
                    context.openTemporaryOutputStream(temporaryFileIdentifier), CHARSET
            );
            // 初始化字段。
            String valueSerializerBeanName = config.getValueSerializerBeanName();
            valueSerializer = ctx.getBean(valueSerializerBeanName, ValueSerializer.class);
            int columnLength = config.getColumns().size();
            columnNames = new String[columnLength];
            fieldNames = new String[columnLength];
            for (int i = 0; i < columnLength; i++) {
                columnNames[i] = config.getColumns().get(i).getColumnName();
                fieldNames[i] = config.getColumns().get(i).getFieldName();
            }
            CSVFormat csvFormat = CSVFormat.Builder.create().setHeader(columnNames).build();
            csvPrinter = new CSVPrinter(temporaryFileWriter, csvFormat);
        } catch (Exception e) {
            LOGGER.warn("执行器执行 beforeExecute 方法时发生异常, 将置位 exceptionFlag 并抛出异常, 异常信息如下: ", e);
            exceptionFlag = true;
            throw e;
        }
    }

    @Override
    public void execute(String identifier, String topic, ReadResult readResult) throws Exception {
        try {
            // 如果 identifier 与 config 中的 identifier 不匹配，则直接返回。
            if (!Objects.equals(identifier, config.getReaderIdentifier())) {
                return;
            }
            // 如果 topic 与 config 中的 topic 不匹配，则直接返回。
            if (!Objects.equals(topic, config.getReaderTopic())) {
                return;
            }
            // 遍历 readResult 中的所有数据，并写入到临时文件中。
            for (Map<String, Object> data : readResult.getDatas()) {
                writeSingleData(data);
            }
        } catch (Exception e) {
            LOGGER.warn("执行器执行 execute 方法时发生异常, 将置位 exceptionFlag 并抛出异常, 异常信息如下: ", e);
            exceptionFlag = true;
            throw e;
        }
    }

    private void writeSingleData(Map<String, Object> data) throws Exception {
        Object[] record = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            Object value = data.get(fieldNames[i]);
            record[i] = valueSerializer.serialize(value);
        }
        csvPrinter.printRecord(record);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void afterExecute() throws Exception {
        // 如果 csvPrinter 为 null，且 temporaryFileWriter 不为 null，则关闭 temporaryFileWriter。
        if (Objects.isNull(csvPrinter) && Objects.nonNull(temporaryFileWriter)) {
            temporaryFileWriter.close();
        }
        // 如果 csvPrinter 不为 null，则关闭 csvPrinter。
        if (Objects.nonNull(csvPrinter)) {
            csvPrinter.close();
        }
        // 如果 exceptionFlag 为 true，不进行后续操作。
        if (exceptionFlag) {
            LOGGER.warn("执行器 exceptionFlag 为 true, 不进行后续操作");
            return;
        }
        // 将临时文件拷贝至目标文件。
        String fileIdentifier = config.getFileIdentifier();
        String fileName = config.getFileName();
        context.copyTemporaryToFile(new TemporaryToFileCopyInfo(
                temporaryFileIdentifier, fileIdentifier, fileName
        ));
    }

    @Override
    public String toString() {
        return "CsvExecutor{" +
                "config=" + config +
                ", ctx=" + ctx +
                ", temporaryFileIdentifier='" + temporaryFileIdentifier + '\'' +
                ", valueSerializer=" + valueSerializer +
                ", columnNames=" + Arrays.toString(columnNames) +
                ", fieldNames=" + Arrays.toString(fieldNames) +
                ", csvPrinter=" + csvPrinter +
                ", exceptionFlag=" + exceptionFlag +
                ", context=" + context +
                '}';
    }
}
