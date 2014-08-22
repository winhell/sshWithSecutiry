package com.wansan.estate.service;

import com.wansan.estate.model.AdCol;
import com.wansan.template.core.Utils;
import com.wansan.template.model.OperEnum;
import com.wansan.template.model.Person;
import com.wansan.template.model.Syslog;
import com.wansan.template.service.BaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2014/8/19.
 */
@Service
public class AdcolService extends BaseDao<AdCol> implements IAdcolService {
    @Override
    public void txDelete(String idList,Person person){
        for(String id:idList.split(",")){
            Query query = getSession().createQuery("delete from AdCol where id = :id");
            Query deleteUnits = getSession().createQuery("delete from AdContent where colID = :id");
            query.setParameter("id",id);
            deleteUnits.setParameter("id",id);
            query.executeUpdate();
            deleteUnits.executeUpdate();
        }
        Syslog syslog = new Syslog();
        syslog.setUserid(person.getName());
        syslog.setName(OperEnum.DELETE.toString());
        syslog.setCreatetime(Utils.getNow());
        syslog.setId(Utils.getNewUUID());
        syslog.setComment("批量删除广告分类："+idList);
        getSession().save(syslog);
    }
}
