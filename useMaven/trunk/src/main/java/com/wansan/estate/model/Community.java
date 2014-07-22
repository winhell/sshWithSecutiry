package com.wansan.estate.model;

import com.wansan.template.model.BasePojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Administrator on 14-7-21.
 */
@Entity
public class Community extends BasePojo{

    private Short cityId;
    private String address;
    private String contacts;
    private String phone;
    private Timestamp updatetime;
    private String estateuser;

    @Basic
    @Column(name = "cityId")
    public Short getCityId() {
        return cityId;
    }

    public void setCityId(Short cityId) {
        this.cityId = cityId;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "contacts")
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "updatetime")
    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    @Basic
    @Column(name = "estateuser")
    public String getEstateuser() {
        return estateuser;
    }

    public void setEstateuser(String estateuser) {
        this.estateuser = estateuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Community community = (Community) o;

        if (address != null ? !address.equals(community.address) : community.address != null) return false;
        if (cityId != null ? !cityId.equals(community.cityId) : community.cityId != null) return false;
        if (comment != null ? !comment.equals(community.comment) : community.comment != null) return false;
        if (contacts != null ? !contacts.equals(community.contacts) : community.contacts != null) return false;
        if (createtime != null ? !createtime.equals(community.createtime) : community.createtime != null) return false;
        if (estateuser != null ? !estateuser.equals(community.estateuser) : community.estateuser != null) return false;
        if (id != null ? !id.equals(community.id) : community.id != null) return false;
        if (name != null ? !name.equals(community.name) : community.name != null) return false;
        if (phone != null ? !phone.equals(community.phone) : community.phone != null) return false;
        if (updatetime != null ? !updatetime.equals(community.updatetime) : community.updatetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        result = 31 * result + (estateuser != null ? estateuser.hashCode() : 0);
        return result;
    }
}
