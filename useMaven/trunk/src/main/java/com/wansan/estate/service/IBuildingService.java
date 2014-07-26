package com.wansan.estate.service;

import com.wansan.estate.model.Building;
import com.wansan.template.service.IBaseDao;

import java.util.List;

/**
 * Created by Administrator on 2014/7/23.
 */
public interface IBuildingService extends IBaseDao<Building> {
    public List getBuildingTree(String id);
    public Object[] getBuildingRooms(String id);
}
