package com.wansan.estate.service;

import com.wansan.template.model.Ofuser;
import com.wansan.template.model.Person;

/**
 * Created by Administrator on 2014/7/29.
 */
public interface IOfuserService {
    public void setFriend(Ofuser user1,Ofuser user2);
    public String save(Ofuser ofuser);
    public Ofuser findGateUserByBuilding(String buildingID);
    public void txAddUser(Ofuser ofuser,Person person);
    public void txDelete(String idList,Person person);
    public void txUpdate(Ofuser ofuser,Person person);
}
