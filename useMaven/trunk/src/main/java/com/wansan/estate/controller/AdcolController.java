package com.wansan.estate.controller;

import com.wansan.estate.model.AdCol;
import com.wansan.estate.model.NoticetypeEnum;
import com.wansan.estate.service.IAdcolService;
import com.wansan.estate.utils.EstateUtils;
import com.wansan.template.controller.BaseController;
import com.wansan.template.model.OperEnum;
import com.wansan.template.model.ResultEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/8/19.
 */
@Controller
@ResponseBody
@RequestMapping(value = "/mainpage/admgr")
public class AdcolController extends BaseController {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private IAdcolService adcolService;

    @RequestMapping(value = "/addadcol")
    public Map<String,Object> add(AdCol adCol){
        try {
            String newID = (String) adcolService.txSave(adCol,getLoginPerson());
            EstateUtils.sendAdNotify(NoticetypeEnum.adcol,newID, OperEnum.CREATE);
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping(value = "/deleteadcol")
    public Map<String,Object> delete(String idList){
        try {
            adcolService.txDelete(idList,getLoginPerson());
            for(String id:idList.split(","))
                EstateUtils.sendAdNotify(NoticetypeEnum.adcol,id,OperEnum.DELETE);
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping(value = "/updateadcol")
    public Map<String,Object> update(AdCol adCol){
        try {
            adcolService.txUpdate(adCol,getLoginPerson());
            EstateUtils.sendAdNotify(NoticetypeEnum.adcol,adCol.getId(),OperEnum.UPDATE);
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping(value = "/listadcol")
    public Map<String,Object> list(int page,int rows){
        Map<String,Object> result = adcolService.findByMap(null,page,rows,"adOrder",true);
        result.put("status", ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/getcolList")
    public List<AdCol> list(){
        return adcolService.listAll();
    }
}
