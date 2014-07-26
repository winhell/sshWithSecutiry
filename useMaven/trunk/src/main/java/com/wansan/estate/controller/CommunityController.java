package com.wansan.estate.controller;

import com.wansan.estate.model.City;
import com.wansan.estate.model.Community;
import com.wansan.estate.service.ICommunityService;
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
 * Created by Administrator on 14-7-21.
 */
@Controller
@ResponseBody
@RequestMapping(value = "/mainpage/estate")
public class CommunityController extends BaseController {

    @Resource
    private ICommunityService communityService;

    private Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/addcommunity")
    public Map<String,Object> add(Community community){
        try {
            communityService.txSave(community,getLoginPerson());
            return returnMap(true,"添加小区成功！");
        }catch (Exception e){
            return returnMap(false,"添加小区失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/updatecommunity")
    public Map<String,Object> update(Community community){
        try {
            communityService.txUpdate(community,getLoginPerson());
            return returnMap(true,"修改小区信息成功！");
        }catch (Exception e){
            return returnMap(false,"修改信息失败！");
        }
    }

    @RequestMapping(value = "/listcommunity")
    public Map<String,Object> list(int page,int rows){
        Map<String,Object> result = communityService.findByMap(null,page,rows,"createtime",false);
        result.put("status", ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/deletecommunity")
    public Map<String,Object> delete(String idList){
        try {
            communityService.txDelete(idList,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping(value = "/listCity")
    public List<City> getCities(Short city){

        return communityService.getCity(city);
    }
}
