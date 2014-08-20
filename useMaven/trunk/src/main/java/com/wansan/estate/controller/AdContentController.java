package com.wansan.estate.controller;

import com.wansan.estate.model.AdContent;
import com.wansan.estate.model.NoticetypeEnum;
import com.wansan.estate.service.IAdcontentService;
import com.wansan.estate.utils.EstateUtils;
import com.wansan.template.controller.BaseController;
import com.wansan.template.model.OperEnum;
import com.wansan.template.model.ResultEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2014/8/19.
 */
@Controller
@RequestMapping(value = "/mainpage/admgr")
@ResponseBody
public class AdContentController extends BaseController {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private IAdcontentService adcontentService;
    
    @RequestMapping(value = "/addadcontent")
    public Map<String,Object> add(AdContent adContent){
        try {
            String newID = (String) adcontentService.txSave(adContent,getLoginPerson());
            EstateUtils.sendAdNotify(NoticetypeEnum.adContent,newID,OperEnum.CREATE);
            return result(true);                 
        }catch(Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }
    
    @RequestMapping(value = "/updateadcontent")
    public Map<String,Object> update(AdContent adContent){
        try {
            adcontentService.txUpdate(adContent,getLoginPerson());
            EstateUtils.sendAdNotify(NoticetypeEnum.adContent,adContent.getId(), OperEnum.UPDATE);
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }
    
    @RequestMapping(value = "/deleteadcontent")
    public Map<String,Object> delete(String idList){
        try {
            adcontentService.txDelete(idList,getLoginPerson());
            for(String id:idList.split(","))
                EstateUtils.sendAdNotify(NoticetypeEnum.adContent,id,OperEnum.DELETE);
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }
    
    @RequestMapping(value = "/listadcontent")
    public Map<String,Object> list(int page,int rows){
        Map<String,Object> result = adcontentService.findByMap(null,page,rows,"createtime",false);
        result.put("status", ResultEnum.SUCCESS);
        return result;
    }
}
