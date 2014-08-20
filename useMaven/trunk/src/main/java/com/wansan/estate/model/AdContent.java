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
@Table(name = "ad_content")
public class AdContent extends BasePojo {
    private String colID;
    private String content;
    private String picture;
    private String audio;
    private String video;
    private Integer priority;

    @Basic
    @Column(name = "colId")
    public String getColID() {
        return colID;
    }

    public void setColID(String colID) {
        this.colID = colID;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "picture")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Basic
    @Column(name = "audio")
    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Basic
    @Column(name = "video")
    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Basic
    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdContent)) return false;

        AdContent adContent = (AdContent) o;

        if (audio != null ? !audio.equals(adContent.audio) : adContent.audio != null) return false;
        if (colID != null ? !colID.equals(adContent.colID) : adContent.colID != null) return false;
        if (content != null ? !content.equals(adContent.content) : adContent.content != null) return false;
        if (picture != null ? !picture.equals(adContent.picture) : adContent.picture != null) return false;
        if (priority != null ? !priority.equals(adContent.priority) : adContent.priority != null) return false;
        if (video != null ? !video.equals(adContent.video) : adContent.video != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = colID != null ? colID.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (audio != null ? audio.hashCode() : 0);
        result = 31 * result + (video != null ? video.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        return result;
    }
}
