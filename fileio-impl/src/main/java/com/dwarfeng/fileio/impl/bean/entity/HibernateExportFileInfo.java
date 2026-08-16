package com.dwarfeng.fileio.impl.bean.entity;

import com.dwarfeng.fileio.impl.bean.key.HibernateTaskItemKey;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(HibernateTaskItemKey.class)
@Table(name = "tbl_export_file_info")
public class HibernateExportFileInfo implements Bean {

    private static final long serialVersionUID = -1743256369027697212L;

    // region 主键

    @Id
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Id
    @Column(name = "identifier", length = Constraints.LENGTH_IDENTIFIER, nullable = false)
    private String identifier;

    // endregion

    // region 主属性字段

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "column_length")
    private long length;

    @Column(name = "generated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date generatedDate;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateExportTask.class)
    @JoinColumns({ //
            @JoinColumn(name = "task_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateExportTask task;

    // endregion

    public HibernateExportFileInfo() {
    }

    // region 映射用属性区

    public HibernateTaskItemKey getKey() {
        return new HibernateTaskItemKey(taskId, identifier);
    }

    public void setKey(HibernateTaskItemKey key) {
        if (Objects.isNull(key)) {
            this.taskId = null;
            this.identifier = null;
        } else {
            this.taskId = key.getTaskId();
            this.identifier = key.getIdentifier();
        }
    }

    // endregion

    // region 常规属性区

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateExportTask getTask() {
        return task;
    }

    public void setTask(HibernateExportTask task) {
        this.task = task;
    }

    // endregion

    @Override
    public String toString() {
        return "HibernateExportFileInfo{" +
                "taskId=" + taskId +
                ", identifier='" + identifier + '\'' +
                ", originName='" + originName + '\'' +
                ", length=" + length +
                ", generatedDate=" + generatedDate +
                ", remark='" + remark + '\'' +
                ", task=" + task +
                '}';
    }
}
