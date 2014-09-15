package com.wansan.estate.service;

import com.wansan.estate.model.CallLog;
import com.wansan.template.service.BaseDao;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/5.
 */
@Service
public class CallLogService extends BaseDao<CallLog> implements ICallLogService {
    @Override
    public Map<String,Object> getCallLogs(String username,int page,int rows,String start,String end){
        Map<String,Object> params = new HashMap<>();
        if(!"".equals(start))
            params.put("startTime >=",start);
        if(!"".equals(end))
            params.put("startTime <=",end);
        params.put("toUser =",username);
        return findByMap(params,page,rows,"startTime",false);
    }
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
        if(!"".equals(params.get("fromuser"))){
            newParams.put("fromuser =", params.get("fromuser"));
        }
        if(!"".equals(params.get("touser"))){
            newParams.put("touser =", params.get("touser"));
        }
        if(!"".equals(params.get("type"))){
            newParams.put("type =", Integer.valueOf(params.get("type")));
        }
        if(!"".equals(params.get("status"))){
            newParams.put("status =", Integer.valueOf(params.get("status")));
        }

        int page = Integer.valueOf(params.get("page"));
        int rows = Integer.valueOf(params.get("rows"));
        return findByMapWithCond(newParams,page,rows,null,false);
    }
}
