package com.dwarfeng.fileio.impl.handler.exporter.dct;

import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.fileio.sdk.handler.exporter.AbstractExecutor;
import com.dwarfeng.fileio.stack.bean.dto.TemporaryToFileCopyInfo;
import com.dwarfeng.fileio.stack.struct.ReadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * 基于 DCT 框架的导出器执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("dctExporterRegistry.dctExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DctExecutor extends AbstractExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DctExecutor.class);

    // 指定换行符为 \n，以兼容不同操作系统。
    private static final String LINE_SEPARATOR = "\n";
    // 指定字符集为 UTF-8，以兼容不同操作系统。
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private final ValueCodingHandler valueCodingHandler;

    private final Config config;

    private String temporaryFileIdentifier;
    private OutputStream temporaryFileOutputStream;

    private boolean firstData;

    private boolean exceptionFlag = false;

    public DctExecutor(ValueCodingHandler valueCodingHandler, Config config) {
        this.valueCodingHandler = valueCodingHandler;
        this.config = config;
    }

    @Override
    public void beforeExecute() throws Exception {
        try {
            // 申请临时文件。
            temporaryFileIdentifier = UUID.randomUUID().toString();
            context.createTemporary(temporaryFileIdentifier);
            // 并打开临时文件的输出流。
            temporaryFileOutputStream = context.openTemporaryOutputStream(temporaryFileIdentifier);
            // 初始化字段。
            firstData = true;
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
                if (firstData) {
                    firstData = false;
                }
            }
        } catch (Exception e) {
            LOGGER.warn("执行器执行 execute 方法时发生异常, 将置位 exceptionFlag 并抛出异常, 异常信息如下: ", e);
            exceptionFlag = true;
            throw e;
        }
    }

    private void writeSingleData(Map<String, Object> data) throws Exception {
        // 定义 StringBuilder sb。
        StringBuilder sb = new StringBuilder();
        // 如果 !firstData, 则向 sb 中添加换行符。
        if (!firstData) {
            sb.append(LINE_SEPARATOR);
        }
        // 遍历 data 中的所有字段，将 data 中的所有字段序列化，并写入到 sb 中。
        boolean firstField = true;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            // 转义 fileName 中的引号。
            fieldName = fieldName.replace("\"", "\\\"");
            // 序列化 fileValue, 并转义引号。
            String serializedFieldValue = valueCodingHandler.encode(fieldValue);
            serializedFieldValue = serializedFieldValue.replace("\"", "\\\"");
            // fieldString = "fieldName":"serializedFieldValue"
            String fieldString = "\"" + fieldName + "\":\"" + serializedFieldValue + "\"";
            // 如果 !firstField, 则向 sb 中添加逗号。
            if (!firstField) {
                sb.append(",");
            }
            // 否则，将 firstField 置为 false。
            else {
                firstField = false;
            }
            // sb = sb + fieldString
            sb.append(fieldString);
        }
        // 将 sb 中的内容以 CHARSET 编码写入到 temporaryFileOutputStream 中。
        temporaryFileOutputStream.write(sb.toString().getBytes(CHARSET));
        temporaryFileOutputStream.flush();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void afterExecute() throws Exception {
        // 如果 temporaryFileOutputStream 不为 null，则关闭。
        if (Objects.nonNull(temporaryFileOutputStream)) {
            temporaryFileOutputStream.close();
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
        return "DctExecutor{" +
                "valueCodingHandler=" + valueCodingHandler +
                ", config=" + config +
                ", temporaryFileIdentifier='" + temporaryFileIdentifier + '\'' +
                ", temporaryFileOutputStream=" + temporaryFileOutputStream +
                ", exceptionFlag=" + exceptionFlag +
                ", context=" + context +
                '}';
    }
}
