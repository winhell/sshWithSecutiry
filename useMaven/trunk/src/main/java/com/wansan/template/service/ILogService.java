package com.wansan.template.service;

import com.wansan.template.model.Syslog;

import java.text.ParseException;
import java.util.Map;

/**
 * Created by Administrator on 14-7-7.
 */
public interface ILogService extends IBaseDao<Syslog> {
    public Map<String,Object> search(Map<String,String> params) throws ParseException;
}
