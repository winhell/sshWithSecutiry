package com.wansan.template.core;

import com.wansan.template.model.ResultEnum;

import java.beans.PropertyEditorSupport;

/**
 * Created by Administrator on 14-4-30.
 */
public class ResultEnumEditor extends PropertyEditorSupport {

    @Override

    public void setAsText(String text) throws IllegalArgumentException {

        int val = Integer.parseInt(text);

        boolean found = false;

        for (ResultEnum d : ResultEnum.values()) {

            if (val == d.ordinal()) {

                this.setValue(d);

                found = true;

                break;

            }

        }

        if (found == false) {

            //错误的取值，我们默认为oracle类型，当然你也可以throws exception

            this.setValue(ResultEnum.SUCCESS);         }

    }

}