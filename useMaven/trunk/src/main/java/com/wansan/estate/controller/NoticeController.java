package com.wansan.estate.controller;

import com.wansan.estate.model.Notice;
import com.wansan.estate.service.INotifyService;
import com.wansan.template.controller.BaseController;
import com.wansan.template.model.ResultEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/8/11.
 */
@Controller
@ResponseBody
@RequestMapping(value = "/mainpage/estate/notice")
public class NoticeController extends BaseController{
    @Resource
    private INotifyService notifyService;

    private Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping("/listNotice")
    public Map<String,Object> list(int page,int rows){
        Map<String,Object> result = notifyService.findByMap(null,page,rows,"createtime",false);
        result.put("status", ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping("/addnotice")
    public Map<String,Object> add(Notice notice){
        try {
            notifyService.txSave(notice,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping("/deletenotice")
    public Map<String,Object> delete(String idList){
        try {
            notifyService.txDelete(idList,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }
}
