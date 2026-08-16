package com.dwarfeng.fileio.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;

/**
 * 导入任务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportTask implements Entity<LongIdKey> {

    private static final long serialVersionUID = 5207695366211358534L;

    private LongIdKey key;
    private LongIdKey settingKey;

    /**
     * @since 1.0.0
     */
    private StringIdKey userKey;

    /**
     * 任务状态。
     *
     * <p>
     * int 枚举，可能的状态为：
     * <ol>
     *     <li>任务创建</li>
     *     <li>任务进行</li>
     *     <li>任务完成</li>
     *     <li>任务失败</li>
     *     <li>任务过期</li>
     *     <li>任务死亡</li>
     * </ol>
     * 详细值参考 sdk 模块的常量工具类。
     */
    private int status;

    private Date createDate;
    private Date startDate;
    private Date endDate;
    private Long duration;
    private int dataCount;
    private String message;
    private Date shouldExpireDate;
    private Date shouldDeadDate;

    public ImportTask() {
    }

    public ImportTask(
            LongIdKey key, LongIdKey settingKey, StringIdKey userKey, int status, Date createDate, Date startDate,
            Date endDate, Long duration, int dataCount, String message, Date shouldExpireDate, Date shouldDeadDate
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

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(LongIdKey settingKey) {
        this.settingKey = settingKey;
    }

    public StringIdKey getUserKey() {
        return userKey;
    }

    public void setUserKey(StringIdKey userKey) {
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
        return "ImportTask{" +
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
