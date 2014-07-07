package com.wansan.template.controller;

import com.wansan.template.model.ResultEnum;
import com.wansan.template.service.ILogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    @RequestMapping(value = "/getLogs")
    public Map<String,Object> getLogs(int page,int rows){
        Map<String,Object> result = logService.findByMap(null,page,rows,"createtime",false);
        result.put("status", ResultEnum.SUCCESS);
        return result;
    }
}
