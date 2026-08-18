package com.dwarfeng.fileio.impl.handler.reader.jdbc;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.List;

/**
 * 配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Config implements Bean {

    private static final long serialVersionUID = 8416860382398052324L;

    @JSONField(name = "#jdbc_driver", ordinal = 1, deserialize = false)
    private String jdbcDriverRem = "JDBC 驱动器的类名，用于指定 JDBC 驱动器的类名，使用类的全限定名。";

    @JSONField(name = "jdbc_driver", ordinal = 2)
    private String jdbcDriver;

    @JSONField(name = "#jdbc_url", ordinal = 3, deserialize = false)
    private String jdbcUrlRem = "JDBC URL，用于指定 JDBC URL，例如 'jdbc:mysql://localhost:3306/test'。";

    @JSONField(name = "jdbc_url", ordinal = 4)
    private String jdbcUrl;

    @JSONField(name = "#jdbc_username", ordinal = 5, deserialize = false)
    private String jdbcUsernameRem = "JDBC 用户名，用于指定 JDBC 连接的用户名。";

    @JSONField(name = "jdbc_username", ordinal = 6)
    private String jdbcUsername;

    @JSONField(name = "#jdbc_password", ordinal = 7, deserialize = false)
    private String jdbcPasswordRem = "JDBC 密码，用于指定 JDBC 连接的密码。";

    @JSONField(name = "jdbc_password", ordinal = 8)
    private String jdbcPassword;

    @JSONField(name = "#sql", ordinal = 9, deserialize = false)
    private String sqlRem = "查询语句，查询参数使用 ? 占位，" +
            "例如 'SELECT * FROM table WHERE created_date BETWEEN ? AND ?'。";

    @JSONField(name = "sql", ordinal = 10)
    private String sql;

    @JSONField(name = "#batch_size", ordinal = 11, deserialize = false)
    private String batchSizeRem = "批量大小, 用于指定每次生成的数据的数量。";

    @JSONField(name = "batch_size", ordinal = 12)
    private int batchSize;

    @JSONField(name = "#max_count", ordinal = 13, deserialize = false)
    private String maxCountRem = "查询数据的最大数量，该值小于等于 6 时表示不限制数量。" +
            "合理地设置此值可以避免在一个任务中导出过多的数据。";

    @JSONField(name = "max_count", ordinal = 14)
    private int maxCount;

    @JSONField(name = "#metadata_identifier", ordinal = 15, deserialize = false)
    private String metadataIdentifierRem = "元数据标识符, 用于指定读取器的元数据对应的标识符。";

    @JSONField(name = "metadata_identifier", ordinal = 16)
    private String metadataIdentifier;

    @JSONField(name = "#result_fields", ordinal = 17, deserialize = false)
    private String resultFieldsRem = "结果字段列表，用于指定查询结果中的字段。" +
            "结果字段列表的长度应该与查询结果中的字段数量一致，且顺序应该与查询结果中的字段顺序一致。";

    @JSONField(name = "result_fields", ordinal = 18)
    private List<String> resultFields;

    @JSONField(name = "#value_coding_handler_bean_name", ordinal = 19, deserialize = false)
    private String valueCodingHandlerBeanNameRem = "ValueCodingHandler Bean 的名称。" +
            "用于将 JDBC 查询参数中的 DCT 文本解码为 Java 对象的处理器。";

    @JSONField(name = "value_coding_handler_bean_name", ordinal = 20)
    private String valueCodingHandlerBeanName;

    public Config() {
    }

    public Config(
            String jdbcDriver, String jdbcUrl, String jdbcUsername, String jdbcPassword,
            String sql, int batchSize, int maxCount, String metadataIdentifier, List<String> resultFields,
            String valueCodingHandlerBeanName
    ) {
        this.jdbcDriver = jdbcDriver;
        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
        this.sql = sql;
        this.batchSize = batchSize;
        this.maxCount = maxCount;
        this.metadataIdentifier = metadataIdentifier;
        this.resultFields = resultFields;
        this.valueCodingHandlerBeanName = valueCodingHandlerBeanName;
    }

    public String getJdbcDriverRem() {
        return jdbcDriverRem;
    }

    public void setJdbcDriverRem(String jdbcDriverRem) {
        this.jdbcDriverRem = jdbcDriverRem;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrlRem() {
        return jdbcUrlRem;
    }

    public void setJdbcUrlRem(String jdbcUrlRem) {
        this.jdbcUrlRem = jdbcUrlRem;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsernameRem() {
        return jdbcUsernameRem;
    }

    public void setJdbcUsernameRem(String jdbcUsernameRem) {
        this.jdbcUsernameRem = jdbcUsernameRem;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPasswordRem() {
        return jdbcPasswordRem;
    }

    public void setJdbcPasswordRem(String jdbcPasswordRem) {
        this.jdbcPasswordRem = jdbcPasswordRem;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getSqlRem() {
        return sqlRem;
    }

    public void setSqlRem(String sqlRem) {
        this.sqlRem = sqlRem;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getBatchSizeRem() {
        return batchSizeRem;
    }

    public void setBatchSizeRem(String batchSizeRem) {
        this.batchSizeRem = batchSizeRem;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public String getMaxCountRem() {
        return maxCountRem;
    }

    public void setMaxCountRem(String maxCountRem) {
        this.maxCountRem = maxCountRem;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public String getMetadataIdentifierRem() {
        return metadataIdentifierRem;
    }

    public void setMetadataIdentifierRem(String metadataIdentifierRem) {
        this.metadataIdentifierRem = metadataIdentifierRem;
    }

    public String getMetadataIdentifier() {
        return metadataIdentifier;
    }

    public void setMetadataIdentifier(String metadataIdentifier) {
        this.metadataIdentifier = metadataIdentifier;
    }

    public String getResultFieldsRem() {
        return resultFieldsRem;
    }

    public void setResultFieldsRem(String resultFieldsRem) {
        this.resultFieldsRem = resultFieldsRem;
    }

    public List<String> getResultFields() {
        return resultFields;
    }

    public void setResultFields(List<String> resultFields) {
        this.resultFields = resultFields;
    }

    public String getValueCodingHandlerBeanNameRem() {
        return valueCodingHandlerBeanNameRem;
    }

    public void setValueCodingHandlerBeanNameRem(String valueCodingHandlerBeanNameRem) {
        this.valueCodingHandlerBeanNameRem = valueCodingHandlerBeanNameRem;
    }

    public String getValueCodingHandlerBeanName() {
        return valueCodingHandlerBeanName;
    }

    public void setValueCodingHandlerBeanName(String valueCodingHandlerBeanName) {
        this.valueCodingHandlerBeanName = valueCodingHandlerBeanName;
    }

    @Override
    public String toString() {
        return "Config{" +
                "jdbcDriverRem='" + jdbcDriverRem + '\'' +
                ", jdbcDriver='" + jdbcDriver + '\'' +
                ", jdbcUrlRem='" + jdbcUrlRem + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", jdbcUsernameRem='" + jdbcUsernameRem + '\'' +
                ", jdbcUsername='" + jdbcUsername + '\'' +
                ", jdbcPasswordRem='" + jdbcPasswordRem + '\'' +
                ", jdbcPassword='" + jdbcPassword + '\'' +
                ", sqlRem='" + sqlRem + '\'' +
                ", sql='" + sql + '\'' +
                ", batchSizeRem='" + batchSizeRem + '\'' +
                ", batchSize=" + batchSize +
                ", maxCountRem='" + maxCountRem + '\'' +
                ", maxCount=" + maxCount +
                ", metadataIdentifierRem='" + metadataIdentifierRem + '\'' +
                ", metadataIdentifier='" + metadataIdentifier + '\'' +
                ", resultFieldsRem='" + resultFieldsRem + '\'' +
                ", resultFields=" + resultFields +
                ", valueCodingHandlerBeanNameRem='" + valueCodingHandlerBeanNameRem + '\'' +
                ", valueCodingHandlerBeanName='" + valueCodingHandlerBeanName + '\'' +
                '}';
    }
}
