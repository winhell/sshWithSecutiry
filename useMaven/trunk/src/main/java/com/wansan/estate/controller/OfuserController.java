package com.wansan.estate.controller;

import com.wansan.estate.service.IOfuserService;
import com.wansan.template.controller.BaseController;
import com.wansan.template.model.Ofuser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    private Logger logger = Logger.getLogger(this.getClass());
    @RequestMapping(value = "/addofuser")
    public Map<String,Object> add(Ofuser ofuser){
        try {
            ofuserService.txAddUser(ofuser,getLoginPerson());
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }
}
