package com.wansan.template.controller;

import com.wansan.estate.model.NoticetypeEnum;
import com.wansan.estate.utils.NoticetypeEditor;
import com.wansan.template.core.CodeEnumEditor;
import com.wansan.template.core.ResultEnumEditor;
import com.wansan.template.model.CodeEnum;
import com.wansan.template.model.Person;
import com.wansan.template.model.ResultEnum;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 14-4-30.
 */
public class BaseController {

    protected Person getLoginPerson(){
        Person oper=null;
        Object principal =  SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            oper = (Person)principal;
        }
        return oper;
    }

    protected Map<String,Object> returnMap(boolean success,String message){
        Map<String,Object> result = new HashMap<>();
        if(success){
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg",message);
        }else {
            result.put("status",ResultEnum.FAIL);
            result.put("msg",message);
        }
        return result;
    }

    protected Map<String,Object> result(boolean success){
        Map<String,Object> result = new HashMap<>();
        if(success){
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg","操作成功！");
        }else {
            result.put("status",ResultEnum.FAIL);
            result.put("msg","操作失败");
        }
        return result;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dfLong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        CustomDateEditor datetimeEditor = new CustomDateEditor(dfLong, true);
        binder.registerCustomEditor(Date.class, dateEditor);
        binder.registerCustomEditor(Timestamp.class, datetimeEditor);
//        binder.registerCustomEditor(CodeEnum.class,new CodeEnumEditor());
//        binder.registerCustomEditor(ResultEnum.class, new ResultEnumEditor());
//        binder.registerCustomEditor(NoticetypeEnum.class,new NoticetypeEditor());
    }
}
