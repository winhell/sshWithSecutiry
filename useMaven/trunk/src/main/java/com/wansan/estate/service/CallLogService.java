package com.wansan.estate.service;

import com.wansan.estate.model.CallLog;
import com.wansan.template.service.BaseDao;
import org.springframework.stereotype.Service;

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
}
