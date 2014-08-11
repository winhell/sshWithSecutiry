package com.wansan.estate.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2014/7/22.
 */
@Entity
@Table(name = "city")
public class City {
    private short id;
    private short parentId;
    private String name;
    private byte type;

    @Id
    @Column(name = "id")
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "parent_id")
    public short getParentId() {
        return parentId;
    }

    public void setParentId(short parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type")
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (parentId != city.parentId) return false;
        if (type != city.type) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (int) parentId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) type;
        return result;
    }
}
