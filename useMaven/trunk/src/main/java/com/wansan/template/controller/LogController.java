package com.wansan.template.controller;

import com.wansan.template.model.ResultEnum;
import com.wansan.template.model.Syslog;
import com.wansan.template.service.ILogService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 14-7-7.
 */
@Controller
@RequestMapping(value = "/mainpage/system")
@ResponseBody
public class LogController extends BaseController {
    @Resource
    private ILogService logService;

    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/getLogs")
    public Map<String,Object> getLogs(int page,int rows){
        Map<String,Object> result = logService.findByMap(null,page,rows,"createtime",false);
        result.put("status", ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/logSearch")
    public Map<String,Object> search(HttpServletRequest request,HttpServletResponse response) throws ParseException {
        Map<String,String> params = new HashMap<>();
        params.put("fromDate",request.getParameter("fromDate"));
        params.put("toDate",request.getParameter("toDate"));
        params.put("name",request.getParameter("name"));
        params.put("userid",request.getParameter("userid"));
        params.put("page",request.getParameter("page"));
        params.put("rows",request.getParameter("rows"));
        Map<String,Object> result = logService.search(params) ;
        result.put("status",ResultEnum.SUCCESS);
        return result;
    }
}
