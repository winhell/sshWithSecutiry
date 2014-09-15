package com.wansan.estate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wansan.template.model.BasePojo;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 14-7-21.
 */
@Entity
@Table(name = "enter_log")
public class EnterLog extends BasePojo{

    private String personId;
    private String unitId;

    @Basic
    @Column(name = "personId")
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "unitId")
    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnterLog enterLog = (EnterLog) o;

        if (comment != null ? !comment.equals(enterLog.comment) : enterLog.comment != null) return false;
        if (createtime != null ? !createtime.equals(enterLog.createtime) : enterLog.createtime != null) return false;
        if (id != null ? !id.equals(enterLog.id) : enterLog.id != null) return false;
        if (name != null ? !name.equals(enterLog.name) : enterLog.name != null) return false;
        if (personId != null ? !personId.equals(enterLog.personId) : enterLog.personId != null) return false;
        if (unitId != null ? !unitId.equals(enterLog.unitId) : enterLog.unitId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        return result;
    }
}
