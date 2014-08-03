package com.wansan.estate.service;

import com.wansan.estate.model.Building;
import com.wansan.estate.model.UsertypeEnum;
import com.wansan.estate.utils.EstateConst;
import com.wansan.template.core.Blowfish;
import com.wansan.template.model.Ofuser;
import com.wansan.template.model.Person;
import org.apache.commons.io.IOUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/7/29.
 */
@Service
public class OfuserService implements IOfuserService {

    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private IBuildingService buildingService;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void setFriend(Ofuser ofuser,Ofuser user2) throws IOException,RuntimeException {
        URL url = new URL(EstateConst.OPENFIREURL +
                "/plugins/updateRosterService/rosterservice/" +
                "certificate=huangxuejian&password=123" +
                "&userlist=" + ofuser.getUsername() +
                "," + user2.getUsername() + "&groups=gate,users");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        if(connection.getResponseCode()==200){
            String result = IOUtils.toString(connection.getInputStream(),"UTF-8");
            if(!result.equals("success"))
                throw new RuntimeException();
        }
    }

    public String save(Ofuser ofuser){
        Blowfish encrpytor = new Blowfish();
        String newPasswd = encrpytor.encrypt(ofuser.getPlainPassword());
        ofuser.setEncryptedPassword(newPasswd);
        return (String) getSession().save(ofuser);
    }

    @Override
    public Ofuser findUserByBuilding(String buildingID) {
        String hql = "from Ofuser where buildingID = :buildingID";
        Query query = getSession().createQuery(hql);
        query.setParameter("buildingID",buildingID);
        return  (Ofuser) query.list().get(0);
    }

    public void txAddUser(Ofuser ofuser,Person person) throws IOException {
        save(ofuser);
        Building commnuity = buildingService.getCommunity(ofuser.getUsername());
        Ofuser communityUser = findUserByBuilding(commnuity.getId());
        setFriend(ofuser,communityUser);

        Building room = buildingService.findById(ofuser.getBuildingID());
        Building gate = buildingService.findById(room.getParent());
        Ofuser gateUser = findUserByBuilding(gate.getId());
        setFriend(ofuser,gateUser);
    }

    @Override
    public void txDelete(String idList, Person person) {
        String[] ids = idList.split(",");
        for(String id:ids){
            Ofuser user = (Ofuser)getSession().get(Ofuser.class, id);
            getSession().delete(user);
        }
    }

    @Override
    public void txUpdate(Ofuser ofuser, Person person) {
        getSession().saveOrUpdate(ofuser);
    }

    public Map<String,Object> getAllUsers(String typeName,int page,int rows){
        Criteria criteria = getSession().createCriteria(Ofuser.class).add(Restrictions.eq("userType",typeName));
        Long total = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null).setResultTransformer(Criteria.ROOT_ENTITY);
        List<Ofuser> list =  criteria.setMaxResults(rows).setFirstResult((page-1)*rows).list();
        Map<String,Object> result = new HashMap<>();
        result.put("total",total);
        result.put("rows",list);
        return result;
    }
}
