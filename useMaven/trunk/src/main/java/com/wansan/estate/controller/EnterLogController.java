package com.wansan.estate.controller;

import com.wansan.estate.service.IEnterLogService;
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
 * Created by Administrator on 2014/9/4.
 */
@Controller
@RequestMapping(value = "/mainpage/estate")
@ResponseBody
public class EnterLogController extends BaseController{
    @Resource
    private IEnterLogService enterLogService;

    @RequestMapping(value = "/getEnterLogs")
    public Map<String,Object> getenterLogs(int page,int rows){
//        Map<String,Object> result = enterLogService.findByMap(null,page,rows,"createtime",false);
        Map<String,Object> result = new HashMap<>();
                result.put("status", ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/enterLogSearch")
    public Map<String,Object> search(HttpServletRequest request,HttpServletResponse response) throws ParseException {
        Map<String,String> params = new HashMap<>();
        params.put("fromDate",request.getParameter("fromDate"));
        params.put("toDate",request.getParameter("toDate"));
        params.put("name",request.getParameter("name"));
        params.put("unitid",request.getParameter("unitid"));
        params.put("personid",request.getParameter("personid"));
        params.put("page",request.getParameter("page"));
        params.put("rows", request.getParameter("rows"));
        Map<String,Object> result = enterLogService.search(params) ;
        result.put("status",ResultEnum.SUCCESS);
        return result;
    }

}
