package com.wansan.template.service;

import com.wansan.template.core.Utils;
import com.wansan.template.model.Person;
import com.wansan.template.model.Resource;
import com.wansan.template.model.RoleResource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 14-4-27.
 */
@Service
public class ResourceService extends BaseDao<Resource> implements IResourceService {

    @javax.annotation.Resource
    private IPersonService personService;

    public List<Resource> getParent(){
        return getChildren("0");
    }

    public List<Resource> getChildren(String parent){
        return findByProperty("parentId",parent);
    }

    public List<Resource> getMenusByRole(String roleId){
        List menus = publicFind("from Resource where id in (select resourceId from RoleResource where roleId ='"+roleId+"') and isMenu='1'");
        Collections.sort(menus, new Comparator<Resource>() {
            @Override
            public int compare(Resource o1, Resource o2) {
                return o1.getShowOrder().compareTo(o2.getShowOrder());
            }
        });
        return menus;
    }

    public List<Resource> getMenusByUsername(String username){
        Person person = personService.findPersonByName(username);
        List roles = publicFind("select roleid from UserRole where userid = '"+person.getId()+"'");
        List<Resource> resources = findInCollectionByPage("from Resource where id in (select resourceId from RoleResource where roleId in :paraList)",roles,-1,0);
        return resources;
    }

    public void txSetRoleResource(String roleId,String idList,boolean isMenu){
        String[] ids = idList.split(",");
        if(isMenu)
            executeQuery("delete from RoleResource where roleId='"+roleId+"' and name = 'menu'");
        else
            executeQuery("delete from RoleResource where roleId='"+roleId+"' and name = 'resource'");

        for(String item:ids){
            RoleResource rolemenu = new RoleResource();
            rolemenu.setId(Utils.getNewUUID());
            rolemenu.setCreatetime(Utils.getNow());
            rolemenu.setResourceId(item);
            rolemenu.setRoleId(roleId);
            getSession().save(rolemenu);
        }
    }

    public Map getAllResource(int page,int rows){
        Map<String,Object> params = new HashMap<>();
        params.put("url <>","");
        params.put("isMenu <>",Byte.valueOf("1"));
        Map list = findByMapWithCond(params,page,rows,"showOrder",true);
        return list;
    }

    public void txDelMenuItem(String idList) {
        String[] ids = idList.split(",");
        for(String id:ids){
            executeQuery("delete from RoleResource where resourceId='"+id+"'");
        }
        delete(idList);
    }
}