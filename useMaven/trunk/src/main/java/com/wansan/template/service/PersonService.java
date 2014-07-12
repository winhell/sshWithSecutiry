package com.wansan.template.service;

import com.wansan.template.core.Blowfish;
import com.wansan.template.core.Utils;
import com.wansan.template.model.Ofuser;
import com.wansan.template.model.OperEnum;
import com.wansan.template.model.Person;
import com.wansan.template.model.Syslog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 14-4-15.
 */

@Service
public class PersonService extends BaseDao<Person> implements IPersonService {
    private Log log = LogFactory.getLog(this.getClass());
    @Override
    public Person findPersonByName(String username) {
        List<Person> persons = findByProperty("name",username);
        if(null==persons||persons.size()==0){
            return null;
        }
        return persons.get(0);
    }

    @Override
    public Serializable txSave(Person person,Person oper){

        String orgiPasswd = person.getPassword();
        person.setPassword(Utils.encodePassword(orgiPasswd,person.getName()));
        Ofuser openfireUser = new Ofuser();
        openfireUser.setUsername(person.getName());
        Blowfish encrpytor = new Blowfish();
        String ofPasswd = encrpytor.encryptString(orgiPasswd);
        openfireUser.setEncryptedPassword(ofPasswd);
        openfireUser.setCreationDate(String.valueOf(new Date().getTime()));
        openfireUser.setModificationDate("0");
        getSession().save(openfireUser);
        log.info(person.getPassword());
        Syslog syslog = new Syslog();
        syslog.setUserid(oper.getName());
        syslog.setName(OperEnum.CREATE.toString());
        syslog.setId(Utils.getNewUUID());
        syslog.setCreatetime(Utils.getNow());
        syslog.setComment("user "+oper.getName()+" 创建用户--> "+person.getName());
        getSession().save(syslog);
        return save(person);
    }

    //Todo: 添加日志信息
    @Override
    public void txDelete(String idList,Person oper){
        String[] ids = idList.split(",");
        for(String id:ids){
            executeQuery("delete from UserRole where userid = '"+id+"'");
        }
        delete(idList);
    }

    //Todo: 添加日志信息
    @Override
    public void txUpdate(Person person,Person oper,boolean cp){
        if(cp)
            person.setPassword(Utils.encodePassword(person.getPassword(),person.getName()));
        saveOrUpdate(person,false);
    }
}
