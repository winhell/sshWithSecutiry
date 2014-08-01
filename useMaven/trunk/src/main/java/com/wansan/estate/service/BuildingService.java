package com.wansan.estate.service;

import com.wansan.estate.model.Building;
import com.wansan.estate.model.Community;
import com.wansan.template.core.Blowfish;
import com.wansan.template.core.Utils;
import com.wansan.template.model.Ofuser;
import com.wansan.template.model.Person;
import com.wansan.template.service.BaseDao;
import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2014/7/26.
 */
@Service
public class BuildingService extends BaseDao<Building> implements IBuildingService {

    @Resource
    private IOfuserService ofuserService;

    @Override
    public Serializable txSave(final Building building,Person person){

        getSession().doWork(
                new Work() {
                    @Override
                    public void execute(Connection connection) throws SQLException {
                        CallableStatement cs = connection.prepareCall("{call InsertBuilding(?,?)}");
                        cs.setString(1,building.getName());
                        cs.setString(2,building.getParent());
                        cs.executeUpdate();
                    }
                }
        );
        return null;
    }

    @Override
    public void txDelete(final String idList,Person person){
        getSession().doWork(
                new Work() {
                    @Override
                    public void execute(Connection connection) throws SQLException {
                        CallableStatement cs = connection.prepareCall("{call DeleteBuilding(?)}");
                        cs.setString(1,idList);
                        cs.executeUpdate();
                    }
                }
        );
    }

    public List<Building> getBuildingTree(String id){
        if("-1".equals(id)){
            return listAll();
        }
        String hql = "select s from Building s,Building p where s.lft between p.lft and p.rgt and p.id = :id";
        Query query = getSession().createQuery(hql);
        query.setParameter("id",id);
        return query.list();
    }

    public Object[] getBuildingRooms(String id){
        String hql =
                "select s.id from Building s,Building p where s.lft between p.lft and p.rgt and s.rgt=s.lft+1 and p.id = :id";
        Query query = getSession().createQuery(hql);
        query.setParameter("id",id);
        return query.list().toArray();
    }

    public List<Ofuser> findUsersByBuilding(String id,int page,int rows){
        String hql = "from Ofuser where buildingID in ("+
                "select s.id from Building s,Building p where s.lft between p.lft and p.rgt and s.rgt=s.lft+1 and p.id = :id)";
        Query query = getSession().createQuery(hql).setMaxResults(rows).setFirstResult((page-1)*rows);
        return query.list();
    }

    public String getBuildingName(String id){
//        Ofuser user = (Ofuser) getSession().get(Ofuser.class,id);
        String hql = "select p from Building s,Building p where p.lft<=s.lft and p.rgt>=s.rgt and s.id = :id order by p.lft";
        Query query = getSession().createQuery(hql).setParameter("id",id);
        List<Building> buildings = query.list();
        StringBuilder sb = new StringBuilder();
        for(Building building:buildings){
            sb.append(building.getText());
        }
        return sb.toString();
    }

    public Building getCommunity(String userID){
        Ofuser user = (Ofuser) getSession().get(Ofuser.class,userID);
        String hql = "select p from Building s,Building p where p.lft<=s.lft and p.rgt>=s.rgt and p.parent='#' and s.id = :id order by p.lft";
        Query query = getSession().createQuery(hql).setParameter("id",user.getBuildingID());
        return (Building) query.list().get(0);
    }

    public void txSetGateUser(String buildingID,Person person){
        String username = "gate"+ Utils.getRandomNumString(8);
        Ofuser user = new Ofuser();
        user.setUsername(username);
        Building building = findById(buildingID);
        user.setBuildingID(building.getParent());
        user.setEncryptedPassword("123");
        user.setCreationDate(String.valueOf(new Date().getTime()));
        user.setModificationDate("0");
        ofuserService.save(user);
    }
}
