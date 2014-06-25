package com.wansan.template.controller;

import com.wansan.template.model.Person;
import com.wansan.template.model.ResultEnum;
import com.wansan.template.service.IPersonService;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 14-4-29.
 */
@Controller
@RequestMapping(value = "/mainpage")
public class UserController extends BaseController{

    Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private IPersonService personService;

    @RequestMapping(value = "/userMgr")
    public String gotoMgr(){
        return "userMgr";
    }

    @RequestMapping(value = "/listuser")
    @ResponseBody
    public Map getUserList(int page,int rows){
        Map<String,Object> result = personService.findByMap(null,page,rows,null,false);
        result.put("status","success");
        return result;
    }

    @RequestMapping(value = "/adduser")
    @ResponseBody
    public Map add(Person person){
        Map<String,Object> result = new HashMap<>();
        Person oper = getLoginPerson(personService);
        try{
            personService.txSave(person,oper);
            result.put("result", ResultEnum.SUCCESS);
            result.put("msg","用户添加成功！");
        }catch (HibernateException e){
            result.put("result",ResultEnum.FAIL);
            result.put("msg","用户添加失败！");
            logger.error("用户添加失败！");
            logger.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteuser")
    @ResponseBody
    public Map delete(String idList){
        Map<String,Object> result = new HashMap<>();
        Person oper = getLoginPerson(personService);
        try{
            personService.txDelete(idList,oper);
            result.put("result",ResultEnum.SUCCESS);
            result.put("msg","用户删除成功！");
        }catch (HibernateException e){
            result.put("result",ResultEnum.FAIL);
            result.put("msg","用户删除失败！");
            logger.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updateuser")
    @ResponseBody
    public Map update(Person person){
        Map<String,Object> result = new HashMap<>();
        Person oper = getLoginPerson(personService);
        try {
            personService.txUpdate(person,oper );
            result.put("result",ResultEnum.SUCCESS);
            result.put("msg","用户修改成功！");
        }catch (HibernateException e){
            result.put("result",ResultEnum.FAIL);
            result.put("msg","修改用户失败！");
            logger.error(e.getMessage());
        }
        return result;
    }
}
