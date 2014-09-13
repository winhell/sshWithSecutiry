package com.wansan.estate.service;

import com.wansan.estate.model.EnterLog;
import com.wansan.template.service.IBaseDao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/4.
 */
public interface IEnterLogService{
    public Map<String,Object> search(Map<String,String> params) throws ParseException;

}
