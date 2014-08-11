package com.wansan.estate.service;

import com.wansan.estate.model.Building;
import com.wansan.estate.utils.EstateUtils;
import com.wansan.template.core.Blowfish;
import com.wansan.template.model.Ofuser;
import com.wansan.template.model.Person;
import com.wansan.template.model.ResultEnum;
import org.apache.commons.io.IOUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.HttpURLConnection;
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
    public void setFriend(String user1,String user2) throws IOException,RuntimeException {
//        URL url = new URL(EstateUtils.getOfSetting("serverUrl") +
//                EstateUtils.getOfSetting("pluginPath") + "/rosterservice/service?" +
//                "certificate=" + EstateUtils.getOfSetting("authUser") + "&password=" + EstateUtils.getOfSetting("authPass") +
//                "&userlist=" + user1 +
//                "," + user2 + "&groups=gate,users");
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.setConnectTimeout(10000);
//        if(connection.getResponseCode()==200){
//            String result = IOUtils.toString(connection.getInputStream(),"UTF-8");
//            if(!result.equals("success"))
//
//        }
        if(EstateUtils.callOfService("/rosterservice/service","userlist="+user1+","+user2+"&groups=gate,users").equals(ResultEnum.FAIL.toString())){
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
    public List<Ofuser> findUserByBuilding(String buildingID) {
        String hql = "from Ofuser where buildingID = :buildingID";
        Query query = getSession().createQuery(hql);
        query.setParameter("buildingID",buildingID);
        return  query.list();
    }

    //todo:添加日志信息
    public void txAddUser(Ofuser ofuser,Person person) throws IOException {

        save(ofuser);
        Building commnuity = buildingService.getCommunity(ofuser.getUsername());
        Ofuser communityUser = findUserByBuilding(commnuity.getId()).get(0);
        setFriend(ofuser.getUsername(),communityUser.getUsername());

        Building room = buildingService.findById(ofuser.getBuildingID());
        Building gate = buildingService.findById(room.getParent());
        //todo:加入多个门口机
        Ofuser gateUser = findUserByBuilding(gate.getId()).get(0);
        setFriend(ofuser.getUsername(),gateUser.getUsername());
    }

    //todo:添加日志信息
    @Override
    public void txDelete(String idList, Person person) {
        String[] ids = idList.split(",");
        for(String id:ids){
            Ofuser user = (Ofuser)getSession().get(Ofuser.class, id);
            getSession().delete(user);
        }
    }

    //todo:添加日志信息
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

    public Ofuser findByUsername(String username){
        return (Ofuser) getSession().load(Ofuser.class,username);
    }
}
