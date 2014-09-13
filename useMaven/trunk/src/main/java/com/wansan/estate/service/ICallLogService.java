package com.wansan.estate.service;

import com.wansan.estate.model.CallLog;
import com.wansan.template.service.IBaseDao;

import java.text.ParseException;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/5.
 */
public interface ICallLogService extends IBaseDao<CallLog>{
    public Map<String,Object> getCallLogs(String username,int page,int rows,String startTime,String endTime);
    public Map<String,Object> search(Map<String,String> params) throws ParseException;
}
