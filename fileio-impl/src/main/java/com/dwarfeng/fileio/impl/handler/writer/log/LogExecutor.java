package com.dwarfeng.fileio.impl.handler.writer.log;

import com.dwarfeng.fileio.sdk.handler.writer.AbstractExecutor;
import com.dwarfeng.fileio.stack.struct.ImportResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 日志写入器执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("logWriterRegistry.logExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LogExecutor extends AbstractExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogExecutor.class);

    private final String logLevel;

    private Map<DataBlockKey, Integer> writeCountStatisticsMap;

    public LogExecutor(String logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public void beforeExecute() throws Exception {
        // 打印执行开始。
        logString("日志写入器执行器执行开始, 将打印所有接收的数据...");
        // 初始化统计字段。
        writeCountStatisticsMap = new HashMap<>();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void execute(String identifier, String topic, ImportResult importResult) throws Exception {
        // 展开参数。
        List<Map<String, Object>> datas = importResult.getDatas();
        // 构造 dataBlockKey。
        DataBlockKey dataBlockKey = new DataBlockKey(identifier, topic);
        // 处理统计。
        int count = writeCountStatisticsMap.getOrDefault(dataBlockKey, 0);
        count += datas.size();
        writeCountStatisticsMap.put(dataBlockKey, count);
        // 打印数据。
        logString("接收数据, identifier: " + identifier + ", topic: " + topic + ", count: " + datas.size());
        for (Map<String, Object> data : datas) {
            String logMessage = "  " + data;
            logString(logMessage);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void afterExecute() throws Exception {
        // 打印执行结束。
        logString("日志写入器执行器执行结束");
        // 打印统计信息。
        logString("统计信息: ");
        for (Map.Entry<DataBlockKey, Integer> entry : writeCountStatisticsMap.entrySet()) {
            String identifier = entry.getKey().getIdentifier();
            String topic = entry.getKey().getTopic();
            int count = entry.getValue();
            String logMessage = "  identifier: " + identifier + ", topic: " + topic + ", count: " + count;
            logString(logMessage);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void logString(String title) throws HandlerException {
        switch (logLevel) {
            case Constants.LEVEL_TRACE:
                LOGGER.trace(title);
                return;
            case Constants.LEVEL_DEBUG:
                LOGGER.debug(title);
                return;
            case Constants.LEVEL_INFO:
                LOGGER.info(title);
                return;
            case Constants.LEVEL_WARN:
                LOGGER.warn(title);
                return;
            case Constants.LEVEL_ERROR:
                LOGGER.error(title);
                return;
            default:
                throw new HandlerException("未知的日志等级: " + logLevel);
        }
    }

    @Override
    public String toString() {
        return "LogExecutor{" +
                "logLevel='" + logLevel + '\'' +
                ", writeCountStatisticsMap=" + writeCountStatisticsMap +
                ", context=" + context +
                '}';
    }

    private static final class DataBlockKey {

        private final String identifier;
        private final String topic;

        public DataBlockKey(String identifier, String topic) {
            this.identifier = identifier;
            this.topic = topic;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getTopic() {
            return topic;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DataBlockKey that = (DataBlockKey) o;
            return Objects.equals(identifier, that.identifier) && Objects.equals(topic, that.topic);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(identifier);
            result = 31 * result + Objects.hashCode(topic);
            return result;
        }

        @Override
        public String toString() {
            return "DataBlockKey{" +
                    "identifier='" + identifier + '\'' +
                    ", topic='" + topic + '\'' +
                    '}';
        }
    }
}
