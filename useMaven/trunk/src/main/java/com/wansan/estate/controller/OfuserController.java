package com.wansan.estate.controller;

import com.wansan.estate.model.UsertypeEnum;
import com.wansan.estate.service.IBuildingService;
import com.wansan.estate.service.IOfuserService;
import com.wansan.template.controller.BaseController;
import com.wansan.template.model.Ofuser;
import com.wansan.template.model.ResultEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2014/7/29.
 */
@Controller
@RequestMapping(value = "/mainpage/estate")
@ResponseBody
public class OfuserController extends BaseController {

    @Resource
    private IOfuserService ofuserService;
    @Resource
    private IBuildingService buildingService;

    private Logger logger = Logger.getLogger(this.getClass());
    @RequestMapping(value = "/addofuser")
    public Map<String,Object> add(Ofuser ofuser){
        try {
            ofuser.setUserType(UsertypeEnum.user.toString());
            ofuser.setPlainPassword("123");
            ofuserService.txAddUser(ofuser,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping(value = "/deleteofuser")
    public Map<String,Object> delete(String idList){
        try {
            ofuserService.txDelete(idList,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping(value = "/updateofuser")
    public Map<String,Object> update(Ofuser ofuser){
        try {
            ofuserService.txUpdate(ofuser,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping(value = "/userlist")
    public Map<String,Object> list(String typeName,int page,int rows){
        Map<String,Object> result = ofuserService.getAllUsers(typeName,page,rows);
        result.put("status", ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/getUserAddress")
    public String getAddressName(String id){
        return buildingService.getBuildingName(id);
    }
}
