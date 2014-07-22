package com.wansan.estate.service;

import com.wansan.estate.model.City;
import com.wansan.estate.model.Community;
import com.wansan.template.core.Blowfish;
import com.wansan.template.core.Utils;
import com.wansan.template.model.Ofuser;
import com.wansan.template.model.OperEnum;
import com.wansan.template.model.Person;
import com.wansan.template.model.Syslog;
import com.wansan.template.service.BaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 14-7-21.
 */
@Service
public class CommunityService extends BaseDao<Community> implements ICommunityService {

    private static final String OriginPass = "8888888";
    @Override
    public Serializable txSave(Community community,Person person){

        //增加openfire用户
        String username = community.getEstateuser();
        Ofuser openfireUser = new Ofuser();
        openfireUser.setUsername(username);
        Blowfish encrpytor = new Blowfish();
        String ofPasswd = encrpytor.encryptString(OriginPass);
        openfireUser.setEncryptedPassword(ofPasswd);
        openfireUser.setCreationDate(String.valueOf(new Date().getTime()));
        openfireUser.setModificationDate("0");
        getSession().save(openfireUser);

        //增加后台系统用户

        //增加日志
        Syslog syslog = new Syslog();
        syslog.setUserid(person.getName());
        syslog.setId(Utils.getNewUUID());
        syslog.setCreatetime(Utils.getNow());
        syslog.setName(OperEnum.CREATE.toString());
        syslog.setComment(person.getName()+"添加小区"+community.getName());
        getSession().save(syslog);

        return save(community);
    }

    @Override
    public void txDelete(String idList,Person person){
        String[] ids = idList.split(",");
        for(String id:ids){
            Community community = findById(id);
            Query query = getSession().createQuery("delete from Ofuser where username=:username");
            query.setParameter("username",community.getEstateuser());
            query.executeUpdate();
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
