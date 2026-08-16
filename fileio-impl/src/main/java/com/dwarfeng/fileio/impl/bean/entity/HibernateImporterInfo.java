package com.dwarfeng.fileio.impl.bean.entity;

import com.dwarfeng.datamark.sdk.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.sdk.jpa.DatamarkField;
import com.dwarfeng.fileio.impl.bean.key.HibernateTaskSettingItemKey;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(HibernateTaskSettingItemKey.class)
@Table(name = "tbl_importer_info")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateImporterInfo implements Bean {

    private static final long serialVersionUID = -5216142148799806320L;

    // region 主键

    @Id
    @Column(name = "task_setting_id", nullable = false)
    private Long taskSettingId;

    @Id
    @Column(name = "identifier", length = Constraints.LENGTH_IDENTIFIER, nullable = false)
    private String identifier;

    // endregion

    // region 主属性字段

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "type", length = Constraints.LENGTH_TYPE)
    private String type;

    @Column(name = "param", columnDefinition = "TEXT")
    private String param;

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

    public HibernateImporterInfo() {
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
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
                "enabled = " + enabled + ", " +
                "type = " + type + ", " +
                "param = " + param + ", " +
                "remark = " + remark + ", " +
                "taskSetting = " + taskSetting + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
