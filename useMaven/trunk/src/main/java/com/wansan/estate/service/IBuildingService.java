package com.wansan.estate.service;

import com.wansan.estate.model.Building;
import com.wansan.template.model.Ofuser;
import com.wansan.template.model.Person;
import com.wansan.template.service.IBaseDao;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2014/7/23.
 */
public interface IBuildingService extends IBaseDao<Building> {
    public List<Building> getBuildingTree(String id);
    public Object[] getBuildingRooms(String id);
    public List<Ofuser> findUsersByBuilding(String id,int page,int rows);
    public String getBuildingName(String id);
    public Building getCommunity(String userID);
    public void txSetGateUser(String buildingID,Person person) throws IOException;
    public String getRoomIDByName(String gateID,String roomName);
    public void txAutoCreate(String buildingID,int unitNum,int floorNum,int roomNum) throws IOException;
}
