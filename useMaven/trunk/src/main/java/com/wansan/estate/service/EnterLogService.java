package com.wansan.estate.service;

import com.wansan.estate.model.EnterLog;
import com.wansan.template.service.BaseDao;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/4.
 */
@Service
public class EnterLogService implements IEnterLogService   {
    public Map<String,Object> search(Map<String,String> params) throws ParseException {
        Map<String,Object> newParams = new HashMap<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        if(!"".equals(params.get("fromDate"))){
            date = format.parse(params.get("fromDate"));
            newParams.put("createtime >=", new Timestamp(date.getTime()));
        }
        if(!"".equals(params.get("toDate"))){
            date = format.parse(params.get("toDate"));
            newParams.put("createtime <=", new Timestamp(date.getTime()));
        }
        if(!"".equals(params.get("name"))){
            newParams.put("name =", params.get("name"));
        }
        if(!"".equals(params.get("unitid"))){
            newParams.put("unitid =", params.get("unitid"));
        }
        if(!"".equals(params.get("personid"))){
            newParams.put("personid =", params.get("personid"));
        }
        int page = Integer.valueOf(params.get("page"));
        int rows = Integer.valueOf(params.get("rows"));
        return null;
    }

}
