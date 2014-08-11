package com.wansan.estate.utils;

import com.wansan.estate.model.NoticetypeEnum;

import java.beans.PropertyEditorSupport;

/**
 * Created by Administrator on 2014/8/11.
 */
public class NoticetypeEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        int val = Integer.parseInt(text);
        boolean found = false;

        for (NoticetypeEnum d : NoticetypeEnum.values()) {
            if (val == d.ordinal()) {
                this.setValue(d);
                found = true;
                break;
            }

        }

        if (!found)
            this.setValue(NoticetypeEnum.broadcast);
    }
}
