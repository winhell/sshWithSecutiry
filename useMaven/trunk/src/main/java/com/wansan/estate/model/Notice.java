package com.wansan.estate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wansan.estate.service.IBuildingService;
import com.wansan.template.core.SpringFactory;
import com.wansan.template.model.BasePojo;

import javax.persistence.*;

/**
 * Created by Administrator on 2014/8/11.
 */
@Entity
@Table(name = "notice")
public class Notice extends BasePojo {
    private NoticetypeEnum type;
    private String to;
    private String content;

    @Basic
    @Enumerated()
    @Column(name = "type")
    public NoticetypeEnum getType(){
        return type;
    }
    public void setType(NoticetypeEnum type){
        this.type = type;
    }

    @Basic
    @Column(name = "to")
    public String getTo(){
        return to;
    }
    public void setTo(String to){
        this.to = to;
    }

    @Basic
    @Column(name = "content")
    @JsonIgnore
    public String getContent(){
        return this.content;
    }
    public void setContent(String content){
        this.content = content;
    }

    @Transient
    public String getReceiver(){
        IBuildingService buildingService = (IBuildingService) SpringFactory.getBean("buildingService");
        if(type==NoticetypeEnum.specical){
            return buildingService.getBuildingName(to);
        }
        return "所有人";
    }
}
