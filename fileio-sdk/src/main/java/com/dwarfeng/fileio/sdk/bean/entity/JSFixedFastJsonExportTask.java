package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 导出任务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonExportTask implements Bean {

    private static final long serialVersionUID = -5820484270283154897L;

    public static JSFixedFastJsonExportTask of(ExportTask exportTask) {
        if (Objects.isNull(exportTask)) {
            return null;
        } else {
            return new JSFixedFastJsonExportTask(
                    JSFixedFastJsonLongIdKey.of(exportTask.getKey()),
                    JSFixedFastJsonLongIdKey.of(exportTask.getSettingKey()),
                    FastJsonStringIdKey.of(exportTask.getUserKey()),
                    exportTask.getStatus(),
                    exportTask.getCreateDate(),
                    exportTask.getStartDate(),
                    exportTask.getEndDate(),
                    exportTask.getDuration(),
                    exportTask.getDataCount(),
                    exportTask.getMessage(),
                    exportTask.getShouldExpireDate(),
                    exportTask.getShouldDeadDate()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "setting_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey settingKey;

    @JSONField(name = "userKey", ordinal = 3)
    private FastJsonStringIdKey userKey;

    @JSONField(name = "status", ordinal = 4)
    private int status;

    @JSONField(name = "create_date", ordinal = 5)
    private Date createDate;

    @JSONField(name = "start_date", ordinal = 6)
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 7)
    private Date endDate;

    @JSONField(name = "duration", ordinal = 8)
    private Long duration;

    @JSONField(name = "data_count", ordinal = 9)
    private int dataCount;

    @JSONField(name = "message", ordinal = 10)
    private String message;

    @JSONField(name = "should_dead_date", ordinal = 11)
    private Date shouldExpireDate;

    @JSONField(name = "last_beat_date", ordinal = 12)
    private Date shouldDeadDate;

    public JSFixedFastJsonExportTask() {
    }

    public JSFixedFastJsonExportTask(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey settingKey, FastJsonStringIdKey userKey, int status,
            Date createDate, Date startDate, Date endDate, Long duration, int dataCount, String message,
            Date shouldExpireDate, Date shouldDeadDate
    ) {
        this.key = key;
        this.settingKey = settingKey;
        this.userKey = userKey;
        this.status = status;
        this.createDate = createDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.dataCount = dataCount;
        this.message = message;
        this.shouldExpireDate = shouldExpireDate;
        this.shouldDeadDate = shouldDeadDate;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(JSFixedFastJsonLongIdKey settingKey) {
        this.settingKey = settingKey;
    }

    public FastJsonStringIdKey getUserKey() {
        return userKey;
    }

    public void setUserKey(FastJsonStringIdKey userKey) {
        this.userKey = userKey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getShouldExpireDate() {
        return shouldExpireDate;
    }

    public void setShouldExpireDate(Date shouldExpireDate) {
        this.shouldExpireDate = shouldExpireDate;
    }

    public Date getShouldDeadDate() {
        return shouldDeadDate;
    }

    public void setShouldDeadDate(Date shouldDeadDate) {
        this.shouldDeadDate = shouldDeadDate;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonExportTask{" +
                "key=" + key +
                ", settingKey=" + settingKey +
                ", userKey=" + userKey +
                ", status=" + status +
                ", createDate=" + createDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                ", dataCount=" + dataCount +
                ", message='" + message + '\'' +
                ", shouldExpireDate=" + shouldExpireDate +
                ", shouldDeadDate=" + shouldDeadDate +
                '}';
    }
}
