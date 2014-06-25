package com.wansan.template.service;

import com.wansan.template.core.Utils;
import com.wansan.template.model.Person;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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

    //Todo: 添加日志信息
    @Override
    public Serializable txSave(Person person,Person oper){
        person.setPassword(Utils.encodePassword(person.getPassword(),person.getName()));
        log.info(person.getPassword());
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
    public void txUpdate(Person person,Person oper){
        person.setPassword(Utils.encodePassword(person.getPassword(),person.getName()));
        saveOrUpdate(person);
    }
}