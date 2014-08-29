package com.wansan.estate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wansan.estate.service.IBuildingService;
import com.wansan.template.core.SpringFactory;
import com.wansan.template.model.BasePojo;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2014/8/11.
 */
@Entity
@Table(name = "notice")
public class Notice extends BasePojo {
    private NoticetypeEnum type;
    private String to;
    private String content;
    private Date endTime;

    @Basic
    @Column(name = "endTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(timezone="GMT+8",pattern = "yyyy-MM-dd HH:mm")
    public Date getEndTime(){
        return endTime;
    }
    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }

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
    @Column(name = "receiverRoom")
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
        switch (type){
            case specical:
                return buildingService.getBuildingName(to);
            case group:
                return "门口机通告";
            case broadcast:
                return "所有人";
            default:
                return "none";
        }
    }
}
