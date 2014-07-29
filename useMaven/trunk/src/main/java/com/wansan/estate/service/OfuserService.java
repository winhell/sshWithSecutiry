package com.wansan.estate.service;

import com.wansan.estate.model.Building;
import com.wansan.template.core.Blowfish;
import com.wansan.template.model.Ofuser;
import com.wansan.template.model.Person;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2014/7/29.
 */
@Service
public class OfuserService implements IOfuserService {

    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private IBuildingService buildingService;
    private Session session = sessionFactory.getCurrentSession();

    @Override
    public void setFriend(Ofuser ofuser,Ofuser userID) {

    }

    public String save(Ofuser ofuser){
        Blowfish encrpytor = new Blowfish();
        String newPasswd = encrpytor.encrypt(ofuser.getEncryptedPassword());
        ofuser.setEncryptedPassword(newPasswd);
        return (String) session.save(ofuser);
    }

    @Override
    public Ofuser findGateUserByBuilding(String buildingID) {
        String hql = "from Ofuser where buildingID = :buildingID";
        Query query = session.createQuery(hql);
        query.setParameter("buildingID",buildingID);
        return  (Ofuser) query.list().get(0);
    }

    public void txAddUser(Ofuser ofuser,Person person){
        save(ofuser);
        Building building = buildingService.getCommunity(ofuser.getUsername());
        Ofuser communityUser = findGateUserByBuilding(building.getId());
        setFriend(ofuser,communityUser);
        Ofuser gateUser = findGateUserByBuilding(ofuser.getBuildingID());
        setFriend(ofuser,gateUser);
    }

    @Override
    public void txDelete(String idList, Person person) {
        String[] ids = idList.split(",");
        for(String id:ids){
            Ofuser user = (Ofuser)session.get(Ofuser.class,id);
            session.delete(user);
        }
    }

    @Override
    public void txUpdate(Ofuser ofuser, Person person) {
        session.saveOrUpdate(ofuser);
    }
}
