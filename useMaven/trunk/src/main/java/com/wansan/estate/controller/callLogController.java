package com.wansan.estate.controller;

import com.wansan.estate.service.ICallLogService;
import com.wansan.template.controller.BaseController;
import com.wansan.template.model.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/13.
 */
@Controller
@RequestMapping(value = "/mainpage/estate")
@ResponseBody
public class callLogController extends BaseController {
    @Resource
    private ICallLogService callLogService;

    @RequestMapping(value = "/getcallLogs")
    public Map<String,Object> getcallLogs(int page,int rows){
        Map<String,Object> result = callLogService.findByMap(null,page,rows,"createtime",false);
        result.put("status", ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/callLogSearch")
    public Map<String,Object> callLogSearch(HttpServletRequest request,HttpServletResponse response) throws ParseException {
        Map<String,String> params = new HashMap<>();
        params.put("fromuser",request.getParameter("fromuser"));
        params.put("touser",request.getParameter("touser"));
        params.put("type",request.getParameter("type"));
        params.put("status",request.getParameter("status"));
        params.put("fromDate",request.getParameter("fromDate"));
        params.put("toDate",request.getParameter("toDate"));
        params.put("page",request.getParameter("page"));
        params.put("rows", request.getParameter("rows"));
        Map<String,Object> result = callLogService.search(params) ;
        result.put("status",ResultEnum.SUCCESS);
        return result;
    }
}
