package com.dwarfeng.fileio.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 用户。
 *
 * @author dwArFeng
 * @since 1.0.0
 */
public class User implements Entity<StringIdKey> {

    private static final long serialVersionUID = 3229640892241431487L;

    private StringIdKey key;
    private String remark;

    public User() {
    }

    public User(StringIdKey key, String remark) {
        this.key = key;
        this.remark = remark;
    }

    @Override
    public StringIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "User{" +
                "key=" + key +
                ", remark='" + remark + '\'' +
                '}';
    }
}
