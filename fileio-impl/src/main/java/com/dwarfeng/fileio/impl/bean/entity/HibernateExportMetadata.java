package com.dwarfeng.fileio.impl.bean.entity;

import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.fileio.impl.bean.key.HibernateTaskItemKey;
import com.dwarfeng.fileio.sdk.util.Constraints;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(HibernateTaskItemKey.class)
@Table(name = "tbl_export_metadata")
public class HibernateExportMetadata implements Bean {

    private static final long serialVersionUID = 5404046783627982166L;

    // region 主键

    @Id
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Id
    @Column(name = "identifier", length = Constraints.LENGTH_IDENTIFIER, nullable = false)
    private String identifier;

    // endregion

    // region 主属性字段

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

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

    public HibernateExportMetadata() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return getClass().getSimpleName() + "(" +
                "taskId = " + taskId + ", " +
                "identifier = " + identifier + ", " +
                "content = " + content + ", " +
                "remark = " + remark + ", " +
                "task = " + task + ")";
    }
}
