package com.wansan.estate.service;

import com.wansan.estate.model.Building;
import com.wansan.estate.model.City;
import com.wansan.estate.model.Community;
import com.wansan.estate.model.UsertypeEnum;
import com.wansan.template.core.Blowfish;
import com.wansan.template.core.Utils;
import com.wansan.template.model.*;
import com.wansan.template.service.BaseDao;
import com.wansan.template.service.IRoleService;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 14-7-21.
 */
@Service
public class CommunityService extends BaseDao<Community> implements ICommunityService {

    private static final String OriginPass = "8888888";
    @Resource
    private IRoleService roleService;
    @Resource
    private IBuildingService buildingService;

    @Override
    public Serializable txSave(Community community,Person person){

        //增加openfire用户
        String newID = Utils.getNewUUID();
        String username = community.getEstateuser();
        Ofuser openfireUser = new Ofuser();
        openfireUser.setUsername(username);
        Blowfish encrpytor = new Blowfish();
        String ofPasswd = encrpytor.encryptString(OriginPass);
        openfireUser.setEncryptedPassword(ofPasswd);
        openfireUser.setCreationDate(String.valueOf(new Date().getTime()));
        openfireUser.setModificationDate("0");
        openfireUser.setUserType(UsertypeEnum.community.toString());
        openfireUser.setBuildingID(newID);
        getSession().save(openfireUser);

        //增加系统用户
        Person adminUser = new Person();
        adminUser.setName(username);
        adminUser.setPassword(Utils.encodePassword(OriginPass, username));
        adminUser.setEnabled(true);
        adminUser.setExpired(false);
        adminUser.setLocked(false);
        adminUser.setId(newID);
        adminUser.setCreatetime(Utils.getNow());
        getSession().save(adminUser);

        //分配用户组
        Role mgrRole = roleService.findByName("ROLE_MGR").get(0);
        UserRole userRole = new UserRole();
        userRole.setId(newID);
        userRole.setCreatetime(Utils.getNow());
        userRole.setRoleid(mgrRole.getId());
        userRole.setUserid(newID);
        getSession().save(userRole);

        //增加地址
        Integer maxRight = (Integer)uniqueResult("select max(rgt) from Building");
        if(null==maxRight)
            maxRight = 0;
        Building building = new Building();
        building.setId(newID);
        building.setCreatetime(Utils.getNow());
        building.setParent("#");
        building.setText(community.getName());
        building.setLft(maxRight+1);
        building.setRgt(maxRight+2);
        getSession().save(building);

        //增加日志
        Syslog syslog = new Syslog();
        syslog.setUserid(person.getName());
        syslog.setId(Utils.getNewUUID());
        syslog.setCreatetime(Utils.getNow());
        syslog.setName(OperEnum.CREATE.toString());
        syslog.setComment(person.getName()+"添加小区信息:"+community.getName());
        getSession().save(syslog);

        community.setId(newID);
        return save(community);
    }

    @Override
    public void txDelete(String idList,Person person){
        String[] ids = idList.split(",");
        for(String id:ids){
            Community community = findById(id);
            Query query = getSession().createQuery("delete from Ofuser where username=:username");
            query.setParameter("username",community.getEstateuser()).executeUpdate();
            Query query1 = getSession().createQuery("delete from Person where name=:username");
            query1.setParameter("username",community.getEstateuser()).executeUpdate();

            buildingService.txDelete(id,person);
            delete(community);
        }

        Syslog syslog = new Syslog();
        syslog.setUserid(person.getName());
        syslog.setId(Utils.getNewUUID());
        syslog.setCreatetime(Utils.getNow());
        syslog.setName(OperEnum.DELETE.toString());
        syslog.setComment(person.getName()+"删除小区"+idList);
        getSession().save(syslog);

    }

    public List<City> getCity(Short code){
        if(null==code||code==0)
            return publicFind("from City where parentId = 1");
        return publicFind("from City where parentId = "+code);
    }

}
