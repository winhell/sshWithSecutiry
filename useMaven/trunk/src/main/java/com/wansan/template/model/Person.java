package com.wansan.template.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wansan.template.core.SpringFactory;
import com.wansan.template.service.IPersonService;
import com.wansan.template.service.IRoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 14-4-15.
 */
@Entity
@Table(name = "person")
public class Person extends BasePojo implements UserDetails{

    private String password;
    private String departId;
    private Timestamp lastlogin;


    private Collection<? extends GrantedAuthority> authorities;

    @Transient
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Transient
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities){
        this.authorities = authorities;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Transient
    @JsonIgnore
    public String getUsername() {
        return this.getName();
    }

    @Transient
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "departId")
    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    @Basic
    @Column(name = "lastlogin")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Timestamp lastlogin) {
        this.lastlogin = lastlogin;
    }

    @Transient
    public String getRolesName(){
        IRoleService service = (IRoleService) SpringFactory.getBean("roleService");
        List<Role> roles = service.getRolesByUser(this);
        StringBuilder result = new StringBuilder();
        for(Role item:roles){
            result.append(item.getComment());
            result.append(";");
        }
        return result.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (comment != null ? !comment.equals(person.comment) : person.comment != null) return false;
        if (createtime != null ? !createtime.equals(person.createtime) : person.createtime != null) return false;
        if (departId != null ? !departId.equals(person.departId) : person.departId != null) return false;
        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (lastlogin != null ? !lastlogin.equals(person.lastlogin) : person.lastlogin != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (password != null ? !password.equals(person.password) : person.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (departId != null ? departId.hashCode() : 0);
        result = 31 * result + (lastlogin != null ? lastlogin.hashCode() : 0);
        return result;
    }
}
