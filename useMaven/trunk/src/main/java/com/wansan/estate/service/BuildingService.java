package com.wansan.estate.service;

import com.wansan.estate.model.Building;
import com.wansan.template.model.Person;
import com.wansan.template.service.BaseDao;
import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2014/7/26.
 */
@Service
public class BuildingService extends BaseDao<Building> implements IBuildingService {
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

    public List getBuildingTree(String id){
        String hql = "select s.id,s.parent,s.text from Building s,Building p where s.lft between p.lft and p.rgt and p.id = :id";
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
}
