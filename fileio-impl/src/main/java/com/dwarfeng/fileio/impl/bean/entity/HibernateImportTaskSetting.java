package com.dwarfeng.fileio.impl.bean.entity;

import com.dwarfeng.datamark.sdk.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.sdk.jpa.DatamarkField;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_import_task_setting")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateImportTaskSetting implements Bean {

    private static final long serialVersionUID = 4364229710157814224L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 主属性字段

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "name", length = Constraints.LENGTH_NAME, nullable = false)
    private String name;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // endregion

    // region 一对多

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateImporterInfo.class, mappedBy = "taskSetting")
    private Set<HibernateImporterInfo> importerInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateWriterInfo.class, mappedBy = "taskSetting")
    private Set<HibernateWriterInfo> writerInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateImportConf.class, mappedBy = "taskSetting")
    private Set<HibernateImportConf> confs = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateImportTemplateInfo.class, mappedBy = "taskSetting")
    private Set<HibernateImportTemplateInfo> templateInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateImportTemplateInfo.class, mappedBy = "taskSetting")
    private Set<HibernateImportTask> tasks = new HashSet<>();

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

    public HibernateImportTaskSetting() {
    }

    // region 映射用属性区

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // endregion

    // region 常规属性区

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<HibernateImporterInfo> getImporterInfos() {
        return importerInfos;
    }

    public void setImporterInfos(Set<HibernateImporterInfo> importerInfos) {
        this.importerInfos = importerInfos;
    }

    public Set<HibernateWriterInfo> getWriterInfos() {
        return writerInfos;
    }

    public void setWriterInfos(Set<HibernateWriterInfo> writerInfos) {
        this.writerInfos = writerInfos;
    }

    public Set<HibernateImportConf> getConfs() {
        return confs;
    }

    public void setConfs(Set<HibernateImportConf> confs) {
        this.confs = confs;
    }

    public Set<HibernateImportTemplateInfo> getTemplateInfos() {
        return templateInfos;
    }

    public void setTemplateInfos(Set<HibernateImportTemplateInfo> templateInfos) {
        this.templateInfos = templateInfos;
    }

    public Set<HibernateImportTask> getTasks() {
        return tasks;
    }

    public void setTasks(Set<HibernateImportTask> tasks) {
        this.tasks = tasks;
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
                "longId = " + longId + ", " +
                "enabled = " + enabled + ", " +
                "name = " + name + ", " +
                "remark = " + remark + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
