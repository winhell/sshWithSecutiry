package com.wansan.estate.model;

import com.wansan.template.model.BasePojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2014/8/18.
 */
@Entity
@Table(name = "ad_col")
public class AdCol extends BasePojo {
    private Integer type;
    private Integer adOrder;

    @Basic
    @Column(name = "typeOrder")
    public Integer getAdOrder() {
        return adOrder;
    }

    public void setAdOrder(Integer adOrder) {
        this.adOrder = adOrder;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
