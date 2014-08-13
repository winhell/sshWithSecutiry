package com.wansan.estate.service;

import com.wansan.estate.model.Building;
import com.wansan.estate.model.UsertypeEnum;
import com.wansan.template.core.Utils;
import com.wansan.template.model.Ofuser;
import com.wansan.template.model.Person;
import com.wansan.template.service.BaseDao;
import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
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

        Building parent = findById(building.getParent());
        Query query = getSession().createQuery("update Building set rgt=rgt+2 where rgt>:pleft");
        query.setParameter("pleft",parent.getLft());
        query.executeUpdate();
        Query query2 = getSession().createQuery("update Building set lft=lft+2 where lft>:pleft");
        query2.setParameter("pleft",parent.getLft());
        query2.executeUpdate();
        Building newbuilding = new Building();
        BeanUtils.copyProperties(building,newbuilding);
        newbuilding.setLft(parent.getLft()+1);
        newbuilding.setRgt(parent.getLft() + 2);
        newbuilding.setText(building.getName());
        return save(newbuilding);
    }

    @Override
    public void txDelete(final String idList,Person person){

        Building building = findById(idList);
        Integer width = building.getRgt()-building.getLft()+1;
        //删除所有相关用户
        Query deleteUser = getSession().createQuery("delete from Ofuser where buildingID in (" +
                "select s.id from Building s,Building p where s.lft between p.lft and p.rgt and p.id = :id)");
        deleteUser.setParameter("id",idList).executeUpdate();

        Query query = getSession().createQuery("delete from Building where lft between :pleft and :pright");
        query.setParameter("pleft",building.getLft()).setParameter("pright",building.getRgt()).executeUpdate();
        Query query1 = getSession().createQuery("update Building set rgt = rgt-:width where rgt>:pright");
        query1.setParameter("width",width).setParameter("pright",building.getRgt()).executeUpdate();
        Query query2 = getSession().createQuery("update Building set lft = lft-:width where lft>:pright");
        query2.setParameter("width",width).setParameter("pright",building.getRgt()).executeUpdate();
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

    public String getRoomIDByName(String gateID,String roomName){
        String hql =
                "select s.id from Building s,Building p where s.lft between p.lft and p.rgt and s.rgt=s.lft+1 and p.id = :id and s.name = :roomName";
        Query query = getSession().createQuery(hql);
        query.setParameter("id",gateID);
        query.setParameter("roomName",roomName);
        return (String) query.list().get(0);
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

    public Building getCommunity(String buildingID){

        String hql = "select p from Building s,Building p where p.lft<=s.lft and p.rgt>=s.rgt and p.parent='#' and s.id = :id order by p.lft";
        Query query = getSession().createQuery(hql).setParameter("id",buildingID);
        return (Building) query.list().get(0);
    }

    public void txSetGateUser(String buildingID,Person person) throws IOException {
        String username = "gate"+ Utils.getRandomNumString(8);
        Ofuser user = new Ofuser();
        user.setUsername(username);
        user.setBuildingID(buildingID);
        user.setPlainPassword("123");
        user.setUserType(UsertypeEnum.gateUser.toString());
        user.setCreationDate(String.valueOf(new Date().getTime()));
        user.setModificationDate("0");
        ofuserService.save(user);

        Building community = getCommunity(buildingID);
        Ofuser communityUser = (Ofuser) publicFind("from Ofuser where buildingID = '"+community.getId()+"'").get(0);
        ofuserService.setFriend(username,communityUser.getUsername());
    }

    public void txAutoCreate(String buildingID,int unitNum,int floorNum,int roomNum) throws IOException {
        String[] unitIDs = new String[unitNum];
        for(int i=0;i<unitNum;i++){
            String unitName = String.valueOf(i+1)+"单元";
            Building building = new Building();
            building.setParent(buildingID);
            building.setText(unitName);
            building.setName(unitName);
            unitIDs[i] = (String) txSave(building, null);
        }
        for(String unitID:unitIDs){
            txSetGateUser(unitID,null);
            for(int f=0;f<floorNum;f++)
                for(int r=0;r<roomNum;r++){
                    String unitName = String.valueOf(f+1)+"0"+String.valueOf(r+1);
                    Building room = new Building();
                    room.setParent(unitID);
                    room.setText(unitName);
                    room.setName(unitName);
                    txSave(room,null);
                }
        }

    }
}
