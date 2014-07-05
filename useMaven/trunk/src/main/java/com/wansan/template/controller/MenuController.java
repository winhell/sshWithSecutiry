package com.wansan.template.controller;

import com.wansan.template.model.Person;
import com.wansan.template.model.ResultEnum;
import com.wansan.template.service.IPersonService;
import com.wansan.template.service.IResourceService;
import org.hibernate.HibernateException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-4-28.
 */
@Controller
@RequestMapping(value = "/mainpage")
public class MenuController extends BaseController{

    @Resource
    private IResourceService resourceService;

    @Resource
    private IPersonService personService;

    @RequestMapping(value = "/menuMgr")
    public String gotoMenu(){
        return "menuMgr";
    }

    @RequestMapping(value = "/getUserMenus")
    @ResponseBody
    public List<com.wansan.template.model.Resource> getMenus(){
        Object principal =  SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails)principal).getUsername();
            return resourceService.getMenusByUsername(username);
        }
        return null;
    }

    @RequestMapping(value = "/getMenus")
    public @ResponseBody List<com.wansan.template.model.Resource> getMenus(HttpServletRequest request){
        String id = request.getParameter("id");
        List<com.wansan.template.model.Resource> tree;
        if(id==null||"".equals(id))
            tree = resourceService.getParent();
        else
            tree = resourceService.getChildren(id);
        return tree;
    }

    //todo:finish the jstree data here
    @RequestMapping(value = "/getMenuTree")
    public @ResponseBody List<Map<String,String>> getMenuTree(String id)
    {
//        String id = request.getParameter("id");
//        List<com.wansan.template.model.Resource> tree;
//        if(id==null||"".equals(id)||"#".equals(id))
//            tree = resourceService.getParent();
//        else
//            tree = resourceService.getChildren(id);
//        List<Map<String,String>> result = new ArrayList<>();
//        for(com.wansan.template.model.Resource item:tree){
//            Map<String,String> menuitem = new HashMap<>();
//            menuitem.put("id",item.getId());
//            if(item.getParentId().equals("0")){
//                menuitem.put("children","true");
//                menuitem.put("type","root");
//                menuitem.put("parent","#");
//            }else{
//                menuitem.put("parent",item.getParentId());
//                menuitem.put("children","false");
//            }
//            menuitem.put("text",item.getName());
//            result.add(menuitem);
//        }
        List<com.wansan.template.model.Resource> menuList = (List<com.wansan.template.model.Resource>) resourceService.getAllMenus(-1,0).get("rows");
        List<Map<String,String>> result = new ArrayList<>();
        for(com.wansan.template.model.Resource menuItem:menuList){
            Map<String,String> newItem = new HashMap<>();
            newItem.put("id",menuItem.getId());
            newItem.put("parent",menuItem.getParentId().equals("0")?"#":menuItem.getParentId());
            newItem.put("text",menuItem.getName());
            result.add(newItem);
        }
        return result;
    }

    @RequestMapping(value = "/listmenus")
    public @ResponseBody Map<String,Object> getMenus(int page,int rows){
        Map<String,Object> result = resourceService.getAllMenus(page,rows);
        result.put("status",ResultEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/addmenu")
    public @ResponseBody Map add(com.wansan.template.model.Resource menu){
        Map<String,Object> result = new HashMap<>();
        byte isMenu = 1;
        menu.setIsMenu(isMenu);
        Person oper = getLoginPerson(personService);
        try{
            if(null==menu.getParentId()||"".equals(menu.getParentId())){
                menu.setParentId("0");
                menu.setState("closed");
            }
            else menu.setState("open");
            resourceService.txSave(menu,oper);
            result.put("status", ResultEnum.SUCCESS);
            result.put("msg","菜单添加完成！");
        }catch (HibernateException e){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","菜单添加失败！");
        }
        return result;
    }

    @RequestMapping(value = "/deletemenu")
    public @ResponseBody Map delete(String idList){
        Person oper = getLoginPerson(personService);
        resourceService.txDelete(idList,oper);
        Map<String,Object> result = new HashMap<>();
        result.put("status",ResultEnum.SUCCESS);
        result.put("msg","菜单项已删除！");
        return result;
    }

    @RequestMapping(value = "/setRoleMenu")
    public @ResponseBody Map setMenu(String idList,String roleId){
        resourceService.txSetRoleResource(roleId,idList,true);
        Map<String,Object> result = new HashMap<>();
        result.put("status",ResultEnum.SUCCESS);
        result.put("msg","菜单设置完成！");
        return result;
    }

    @RequestMapping(value = "/getMenusByRole")
    public @ResponseBody String getMenusByRole(String roleId){
        List<com.wansan.template.model.Resource> resources = resourceService.getMenusByRole(roleId);
        StringBuilder sb = new StringBuilder();
        for(com.wansan.template.model.Resource item:resources){
            sb.append(item.getId());
            sb.append(",");
        }
        return sb.toString();
    }
}
