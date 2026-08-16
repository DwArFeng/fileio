package com.dwarfeng.fileio.impl.bean.entity;

import com.dwarfeng.datamark.sdk.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.sdk.jpa.DatamarkField;
import com.dwarfeng.fileio.impl.bean.key.HibernateTaskSettingItemKey;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(HibernateTaskSettingItemKey.class)
@Table(name = "tbl_import_template_info")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateImportTemplateInfo implements Bean {

    private static final long serialVersionUID = -8137595187267793315L;

    // region 主键

    @Id
    @Column(name = "task_setting_id", nullable = false)
    private Long taskSettingId;

    @Id
    @Column(name = "identifier", length = Constraints.LENGTH_IDENTIFIER, nullable = false)
    private String identifier;

    // endregion

    // region 主属性字段

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "column_length")
    private long length;

    @Column(name = "uploaded_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedDate;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateImportTaskSetting.class)
    @JoinColumns({ //
            @JoinColumn(name = "task_setting_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateImportTaskSetting taskSetting;

    // endregion

    // region 审计

    @DatamarkField(handlerName = "importTaskSettingDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "importTaskSettingDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    // endregion

    public HibernateImportTemplateInfo() {
    }

    // region 映射用属性区

    public HibernateTaskSettingItemKey getKey() {
        return new HibernateTaskSettingItemKey(taskSettingId, identifier);
    }

    public void setKey(HibernateTaskSettingItemKey key) {
        if (Objects.isNull(key)) {
            this.taskSettingId = null;
            this.identifier = null;
        } else {
            this.taskSettingId = key.getTaskSettingId();
            this.identifier = key.getIdentifier();
        }
    }

    // endregion

    // region 常规属性区

    public Long getTaskSettingId() {
        return taskSettingId;
    }

    public void setTaskSettingId(Long taskSettingId) {
        this.taskSettingId = taskSettingId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateImportTaskSetting getTaskSetting() {
        return taskSetting;
    }

    public void setTaskSetting(HibernateImportTaskSetting taskSetting) {
        this.taskSetting = taskSetting;
    }

    public String getCreatedDatamark() {
        return createdDatamark;
    }

    public void setCreatedDatamark(String createdDatamark) {
        this.createdDatamark = createdDatamark;
    }

    public String getModifiedDatamark() {
        return modifiedDatamark;
    }

    public void setModifiedDatamark(String modifiedDatamark) {
        this.modifiedDatamark = modifiedDatamark;
    }

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "taskSettingId = " + taskSettingId + ", " +
                "identifier = " + identifier + ", " +
                "originName = " + originName + ", " +
                "length = " + length + ", " +
                "uploadedDate = " + uploadedDate + ", " +
                "remark = " + remark + ", " +
                "taskSetting = " + taskSetting + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
