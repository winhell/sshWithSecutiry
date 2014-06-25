package com.wansan.template.service;

import com.wansan.template.model.Person;
import com.wansan.template.model.Role;
import com.wansan.template.model.UserRole;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 14-4-15.
 */

@Service
public class RoleService extends BaseDao<Role> implements IRoleService {
    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getRolesByUser(Person person){
        List<UserRole> userRoles = publicFind("from UserRole where userid = '" + person.getId() + "'");
        List<Role> result = new ArrayList<Role>();
        for(UserRole userRole:userRoles){
            Role role = findById(userRole.getRoleid());
            result.add(role);
        }
        return result;
    }

    @Override
    public void txDelete(String idList,Person oper){
        String[] ids = idList.split(",");
        for(String id:ids){
            executeQuery("delete from UserRole where roleId = '"+id+"'");
            executeQuery("delete from RoleResource where roldid '="+id+"'");
        }
        delete(idList);

    }

}