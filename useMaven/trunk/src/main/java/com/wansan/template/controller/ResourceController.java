package com.wansan.template.controller;

import com.wansan.template.model.Person;
import com.wansan.template.model.ResultEnum;
import com.wansan.template.service.IPersonService;
import com.wansan.template.service.IResourceService;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 14-4-30.
 */
@Controller
@RequestMapping(value = "/jsp")
public class ResourceController extends BaseController {
    @Resource
    private IResourceService resourceService;

    @Resource
    private IPersonService personService;

    private Logger log = Logger.getLogger(this.getClass());
    @RequestMapping(value = "/resourceMgr")
    public String gotoMgr(){
        return "resourceMgr";
    }

    @RequestMapping(value = "/getAllResources")
    public @ResponseBody Map getAllResources(int page,int rows){
        return resourceService.getAllResource(page,rows);
    }

    @RequestMapping(value = "/addresource")
    public @ResponseBody Map add(com.wansan.template.model.Resource resource){
        Map<String,Object> result =new HashMap<>();
        Person oper = getLoginPerson(personService);
        try{
            resource.setName("resource");
            resourceService.txSave(resource,oper);
            result.put("status", ResultEnum.SUCCESS);
            result.put("msg","成功添加资源!");
        }catch (HibernateException e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","添加资源失败！");
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteresource")
    public @ResponseBody Map delete(String idList){
        Map<String,Object> result =new HashMap<>();
        Person oper = getLoginPerson(personService);
        try{
            resourceService.txDelete(idList, oper);
            result.put("status", ResultEnum.SUCCESS);
            result.put("msg","删除添加资源!");
        }catch (HibernateException e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","删除资源失败！");
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updateresource")
    public @ResponseBody Map update(com.wansan.template.model.Resource resource){
        Map<String,Object> result =new HashMap<>();
        Person oper = getLoginPerson(personService);
        try{
            resourceService.txUpdate(resource,oper);
            result.put("status", ResultEnum.SUCCESS);
            result.put("msg","成功修改资源!");
        }catch (HibernateException e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","修改资源失败！");
            log.error(e.getMessage());
        }
        return result;

    }
}
