package com.dwarfeng.fileio.impl.handler.reader.mock;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fileio.sdk.handler.reader.AbstractExecutor;
import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.fileio.stack.struct.ReadResult;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 模拟执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("mockReaderRegistry.mockExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MockExecutor extends AbstractExecutor {

    private final Config config;

    private RandomGenerator randomGenerator;

    private int totalSize;
    private int batchSize;
    private int generatedCount = 0;

    public MockExecutor(Config config) {
        this.config = config;
    }

    @Override
    public Set<String> topicSpace() {
        return Collections.singleton(null);
    }

    @Override
    public void beforeExecute() throws Exception {
        randomGenerator = new RandomGenerator(config.getRandomSeed());
        ExportMetadata exportMetadata = context.getMetadata(config.getMetadataIdentifier());
        Metadata metadata = JSON.parseObject(exportMetadata.getContent(), Metadata.class);
        totalSize = Math.max(metadata.getTotalSize(), 0);
        batchSize = Math.max(config.getBatchSize(), 1);
    }

    @Override
    public ReadResult execute(String topic) {
        if (generatedCount >= totalSize) {
            return new ReadResult(false, Collections.emptyList());
        }

        int count = Math.min(batchSize, totalSize - generatedCount);
        List<Map<String, Object>> datas = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            datas.add(generateSingleData());
        }

        generatedCount += count;
        return new ReadResult(generatedCount < totalSize, datas);
    }

    private Map<String, Object> generateSingleData() {
        Map<String, Object> data = new HashMap<>();
        for (Config.FieldItem fieldItem : config.getFieldItems()) {
            String fieldName = fieldItem.getFieldName();
            String fieldType = fieldItem.getFieldType();
            Object value = FieldTypeUtil.generateNextRandomValue(randomGenerator, fieldType);
            data.put(fieldName, value);
        }
        return data;
    }

    @Override
    public void afterExecute() {
    }

    @Override
    public String toString() {
        return "MockExecutor{" +
                "config=" + config +
                ", randomGenerator=" + randomGenerator +
                ", totalSize=" + totalSize +
                ", batchSize=" + batchSize +
                ", generatedCount=" + generatedCount +
                ", context=" + context +
                '}';
    }
}
