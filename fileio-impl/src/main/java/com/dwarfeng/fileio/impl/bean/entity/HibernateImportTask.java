package com.dwarfeng.fileio.impl.bean.entity;

import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_import_task")
public class HibernateImportTask implements Bean {

    private static final long serialVersionUID = -3991297773047663975L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 外键

    @Column(name = "setting_id")
    private Long settingLongId;

    @Column(name = "user_id")
    private String userStringId;

    // endregion

    // region 主属性字段

    @Column(name = "status")
    private int status;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "data_count")
    private int dataCount;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE)
    private String message;

    @Column(name = "should_expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shouldExpireDate;

    @Column(name = "should_dead_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shouldDeadDate;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateImportTaskSetting.class)
    @JoinColumns({ //
            @JoinColumn(name = "setting_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateImportTaskSetting taskSetting;

    @ManyToOne(targetEntity = HibernateUser.class)
    @JoinColumns({ //
            @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateUser user;

    // endregion

    // region 一对多

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateImportFileInfo.class, mappedBy = "task")
    private Set<HibernateImportFileInfo> fileInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateImportMetadata.class, mappedBy = "task")
    private Set<HibernateImportMetadata> metadatas = new HashSet<>();

    // endregion

    public HibernateImportTask() {
    }

    // region 映射用属性区

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateLongIdKey getSettingKey() {
        return Optional.ofNullable(settingLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setSettingKey(HibernateLongIdKey key) {
        this.settingLongId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateStringIdKey getUserKey() {
        return Optional.ofNullable(userStringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setUserKey(HibernateStringIdKey key) {
        this.userStringId = Optional.ofNullable(key).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    // endregion

    // region 常规属性区

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public Long getSettingLongId() {
        return settingLongId;
    }

    public void setSettingLongId(Long settingLongId) {
        this.settingLongId = settingLongId;
    }

    public String getUserStringId() {
        return userStringId;
    }

    public void setUserStringId(String userStringId) {
        this.userStringId = userStringId;
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

    public HibernateImportTaskSetting getTaskSetting() {
        return taskSetting;
    }

    public void setTaskSetting(HibernateImportTaskSetting taskSetting) {
        this.taskSetting = taskSetting;
    }

    public HibernateUser getUser() {
        return user;
    }

    public void setUser(HibernateUser user) {
        this.user = user;
    }

    public Set<HibernateImportFileInfo> getFileInfos() {
        return fileInfos;
    }

    public void setFileInfos(Set<HibernateImportFileInfo> fileInfos) {
        this.fileInfos = fileInfos;
    }

    public Set<HibernateImportMetadata> getMetadatas() {
        return metadatas;
    }

    public void setMetadatas(Set<HibernateImportMetadata> metadatas) {
        this.metadatas = metadatas;
    }

    // endregion

    @SuppressWarnings("DuplicatedCode")
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "settingLongId = " + settingLongId + ", " +
                "userStringId = " + userStringId + ", " +
                "status = " + status + ", " +
                "createDate = " + createDate + ", " +
                "startDate = " + startDate + ", " +
                "endDate = " + endDate + ", " +
                "duration = " + duration + ", " +
                "dataCount = " + dataCount + ", " +
                "message = " + message + ", " +
                "shouldExpireDate = " + shouldExpireDate + ", " +
                "shouldDeadDate = " + shouldDeadDate + ", " +
                "taskSetting = " + taskSetting + ", " +
                "user = " + user + ")";
    }
}
