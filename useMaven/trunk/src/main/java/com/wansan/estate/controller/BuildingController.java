package com.wansan.estate.controller;

import com.wansan.estate.model.Building;
import com.wansan.estate.service.IBuildingService;
import com.wansan.template.controller.BaseController;
import com.wansan.template.model.Person;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/7/28.
 */
@Controller
@RequestMapping(value = "/mainpage/estate")
@ResponseBody
public class BuildingController extends BaseController {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private IBuildingService buildingService;

    @RequestMapping("/getBuildingTree")
    public List<Building> getBuildingTree(String id){
        Person person = getLoginPerson();
        if(null==person.getComment()||"".equals(person.getComment()))
            return buildingService.getBuildingTree(id);
        else
            return buildingService.getBuildingTree(person.getComment());
    }

    @RequestMapping("/addbuilding")
    public Map<String,Object> add(Building  building,String isGate){
        try {
            String buildingID = (String) buildingService.txSave(building,getLoginPerson());
            if(null!=isGate&&!"".equals(isGate))
                buildingService.txSetGateUser(buildingID,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping("/deletebuilding")
    public Map<String,Object> delete(String id){
        try {
            buildingService.txDelete(id,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }

    @RequestMapping(value = "/autoCreate")
    public Map<String,Object> create(String parentID,int floorNum,int unitNum,int roomNum){
        try {
            buildingService.txAutoCreate(parentID,unitNum,floorNum,roomNum);
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }

    }
}
