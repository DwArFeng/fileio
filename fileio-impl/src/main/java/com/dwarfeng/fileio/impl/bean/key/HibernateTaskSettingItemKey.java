package com.dwarfeng.fileio.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

public class HibernateTaskSettingItemKey implements Key {

    private static final long serialVersionUID = -2366597431291268462L;

    private Long taskSettingId;
    private String identifier;

    public HibernateTaskSettingItemKey() {
    }

    public HibernateTaskSettingItemKey(Long taskSettingId, String identifier) {
        this.taskSettingId = taskSettingId;
        this.identifier = identifier;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HibernateTaskSettingItemKey that = (HibernateTaskSettingItemKey) o;

        if (!Objects.equals(taskSettingId, that.taskSettingId))
            return false;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        int result = taskSettingId != null ? taskSettingId.hashCode() : 0;
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HibernateTaskSettingItemKey{" +
                "taskSettingId=" + taskSettingId +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
