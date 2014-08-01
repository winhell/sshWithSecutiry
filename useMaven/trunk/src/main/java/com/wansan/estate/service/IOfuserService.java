package com.wansan.estate.service;

import com.wansan.template.model.Ofuser;
import com.wansan.template.model.Person;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/7/29.
 */
public interface IOfuserService {
    public void setFriend(Ofuser user1,Ofuser user2) throws IOException;
    public String save(Ofuser ofuser);
    public Ofuser findGateUserByBuilding(String buildingID);
    public void txAddUser(Ofuser ofuser,Person person) throws IOException;
    public void txDelete(String idList,Person person);
    public void txUpdate(Ofuser ofuser,Person person);
    public Map<String,Object> getAllUsers(int page,int rows);
}
