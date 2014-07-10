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
@ResponseBody
@RequestMapping(value = "/mainpage/system")
public class ResourceController extends BaseController {
    @Resource
    private IResourceService resourceService;

    @Resource
    private IPersonService personService;

    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/getAllResources")
    public  Map getAllResources(int page,int rows){
        Map result =  resourceService.getAllResource(page,rows);
        result.put("status",ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/addresource")
    public Map add(com.wansan.template.model.Resource resource){
        Map<String,Object> result =new HashMap<>();
        Person oper = getLoginPerson(personService);
        try{
            resource.setName("resource");
            byte isMenu = 0;
            resource.setIsMenu(isMenu);
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
    public Map delete(String idList){
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
    public Map update(com.wansan.template.model.Resource resource){
        Map<String,Object> result =new HashMap<>();
        Person oper = getLoginPerson(personService);
        try{
            byte isMenu = 0;
            resource.setIsMenu(isMenu);
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

    @RequestMapping(value = "/assignRoles")
    public Map<String,Object> assign(String resourceId,String idList){
        Map<String,Object> result = new HashMap<>();
        try {
            resourceService.txSetRoleResource(resourceId,idList,false);
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg","角色资源分配成功！");
        } catch (Exception e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","角色资源分配失败，数据库读写错误！");
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getResourceRoles")
    public String getRoles(String resourceId){
        return resourceService.getRolesByResource(resourceId);
    }
}
