package com.wansan.template.model;

import javax.persistence.*;

/**
 * Created by Administrator on 14-6-30.
 */
@Entity
@Table(name = "Ofuser")
public class Ofuser {
    private String username;
    private String plainPassword;
    private String encryptedPassword;
    private String name;
    private String email;
    private String creationDate;
    private String modificationDate;

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "plainPassword")
    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    @Basic
    @Column(name = "encryptedPassword")
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "creationDate")
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "modificationDate")
    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ofuser ofuser = (Ofuser) o;

        if (creationDate != null ? !creationDate.equals(ofuser.creationDate) : ofuser.creationDate != null)
            return false;
        if (email != null ? !email.equals(ofuser.email) : ofuser.email != null) return false;
        if (encryptedPassword != null ? !encryptedPassword.equals(ofuser.encryptedPassword) : ofuser.encryptedPassword != null)
            return false;
        if (modificationDate != null ? !modificationDate.equals(ofuser.modificationDate) : ofuser.modificationDate != null)
            return false;
        if (name != null ? !name.equals(ofuser.name) : ofuser.name != null) return false;
        if (plainPassword != null ? !plainPassword.equals(ofuser.plainPassword) : ofuser.plainPassword != null)
            return false;
        if (username != null ? !username.equals(ofuser.username) : ofuser.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (plainPassword != null ? plainPassword.hashCode() : 0);
        result = 31 * result + (encryptedPassword != null ? encryptedPassword.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
        return result;
    }
}
