package com.wansan.template.model;

/**
 * Created by Administrator on 14-7-13.
 */
public enum CodeEnum {
    sex("性别"),
    feather("政治面貌");

    private String value;
    private CodeEnum(String value){
        this.value = value;
    }

    public String toString(){
        return this.value;
    }
}
