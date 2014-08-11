package com.wansan.estate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wansan.template.model.BasePojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2014/7/23.
 */
@Entity
@Table(name = "building")
public class Building extends BasePojo {
    private String text;
    private String parent;
    private Integer rgt;
    private Integer lft;

    @Basic
    @Column(name = "text")
    public String getText(){
        return this.text;
    }
    public void setText(String text){
        this.text = text;
    }

    @Basic
    @Column(name = "parent")
    public String getParent(){
        return this.parent;
    }

    public void setParent(String parent){
        this.parent = parent;
    }

    @Basic
    @Column(name = "rgt")
    @JsonIgnore
    public Integer getRgt(){
        return this.rgt;
    }
    public void setRgt(Integer rgt){
        this.rgt = rgt;
    }

    @Basic
    @Column(name = "lft")
    @JsonIgnore
    public Integer getLft(){
        return this.lft;
    }

    public void setLft(Integer lft){
        this.lft = lft;
    }

}
