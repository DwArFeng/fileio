package com.dwarfeng.fileio.impl.handler.reader.jdbc;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.fileio.sdk.handler.reader.AbstractExecutor;
import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.fileio.stack.struct.ReadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * JDBC 读取器执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("jdbcReaderRegistry.jdbcExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JdbcExecutor extends AbstractExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcExecutor.class);

    private final Config config;

    private final ValueCodingHandler valueCodingHandler;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Boolean cachedHasNext;

    private int maxCount;
    private int readCount = 0;

    public JdbcExecutor(Config config, ValueCodingHandler valueCodingHandler) {
        this.config = config;
        this.valueCodingHandler = valueCodingHandler;
    }

    @Override
    public Set<String> topicSpace() {
        return Collections.singleton(null);
    }

    @Override
    public void beforeExecute() throws Exception {
        // 展开参数。
        String jdbcDriver = config.getJdbcDriver();
        String jdbcUrl = config.getJdbcUrl();
        String jdbcUsername = config.getJdbcUsername();
        String jdbcPassword = config.getJdbcPassword();
        String sql = config.getSql();
        String metadataIdentifier = config.getMetadataIdentifier();

        // 基于配置初始化参数。
        maxCount = config.getMaxCount();
        // 如果 maxCount 小于等于 0，则不限制导出数量。
        if (maxCount <= 0) {
            // Integer.MAX_VALUE 在该场景下可以表示无穷大。
            maxCount = Integer.MAX_VALUE;
        }

        // 关键参数非 null 判断。
        Objects.requireNonNull(jdbcDriver, "jdbcDriver 为 null");
        Objects.requireNonNull(jdbcUrl, "jdbcUrl 为 null");
        Objects.requireNonNull(sql, "sql 为 null");

        // 读取元数据。
        ExportMetadata exportMetadata = context.getMetadata(metadataIdentifier);
        Metadata metadata = JSON.parseObject(exportMetadata.getContent(), Metadata.class);

        // 初始化连接。
        Class.forName(jdbcDriver);
        connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);

        // 初始化预处理语句。
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < metadata.getQueryParams().size(); i++) {
            int pi = i + 1;
            Object x = valueCodingHandler.decode(metadata.getQueryParams().get(i));
            preparedStatement.setObject(pi, x);
        }

        // 执行查询，并初始化结果集合。
        resultSet = preparedStatement.executeQuery();
    }

    @Override
    public ReadResult execute(String topic) throws Exception {
        // 展开参数。
        int batchSize = config.getBatchSize();

        // 如果已经读取了足够的数据，则不再继续读取。
        if (readCount >= maxCount) {
            return new ReadResult(false, Collections.emptyList());
        }

        // 一直读取数据，直到读取到 batchSize 个数据或者读取到结果集合的末尾。
        List<Map<String, Object>> datas = new ArrayList<>(batchSize);
        boolean hasNext;
        int batchCount = 0;
        while (true) {
            hasNext = resultSetHasNext() && readCount < maxCount;
            if (!hasNext) {
                break;
            }
            if (batchCount >= batchSize) {
                break;
            }
            resultSetNext();
            datas.add(readSingleData());
            batchCount++;
            readCount++;
        }

        // 返回结果。
        return new ReadResult(hasNext, datas);
    }

    private boolean resultSetHasNext() throws Exception {
        if (Objects.nonNull(cachedHasNext)) {
            return cachedHasNext;
        }
        cachedHasNext = resultSet.next();
        return cachedHasNext;
    }

    private void resultSetNext() {
        if (Objects.isNull(cachedHasNext)) {
            throw new IllegalStateException("cachedHasNext is null");
        }
        if (!cachedHasNext) {
            throw new IllegalStateException("cachedHasNext is false");
        }
        cachedHasNext = null;
    }

    private Map<String, Object> readSingleData() throws Exception {
        // 展开参数。
        List<String> resultFields = config.getResultFields();

        // 读取数据。
        Map<String, Object> data = new HashMap<>();
        for (int i = 0; i < resultFields.size(); i++) {
            int ci = i + 1;
            Object x = resultSet.getObject(ci);
            data.put(resultFields.get(i), x);
        }
        return data;
    }

    @Override
    public void afterExecute() {
        // 关闭结果集合。
        try {
            if (Objects.nonNull(resultSet)) {
                resultSet.close();
            }
        } catch (Exception e) {
            LOGGER.warn("关闭结果集合时发生异常, 异常信息如下: ", e);
        }

        // 关闭预处理语句。
        try {
            if (Objects.nonNull(preparedStatement)) {
                preparedStatement.close();
            }
        } catch (Exception e) {
            LOGGER.warn("关闭预处理语句时发生异常, 异常信息如下: ", e);
        }

        // 关闭连接。
        try {
            if (Objects.nonNull(connection)) {
                connection.close();
            }
        } catch (Exception e) {
            LOGGER.warn("关闭连接时发生异常, 异常信息如下: ", e);
        }
    }

    @Override
    public String toString() {
        return "JdbcExecutor{" +
                "config=" + config +
                ", valueCodingHandler=" + valueCodingHandler +
                ", connection=" + connection +
                ", preparedStatement=" + preparedStatement +
                ", resultSet=" + resultSet +
                ", cachedHasNext=" + cachedHasNext +
                ", maxCount=" + maxCount +
                ", readCount=" + readCount +
                ", context=" + context +
                '}';
    }
}
