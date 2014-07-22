package com.wansan.estate.service;

import com.wansan.estate.model.City;
import com.wansan.estate.model.Community;
import com.wansan.template.service.IBaseDao;

import java.util.List;


/**
 * Created by Administrator on 14-7-21.
 */
public interface ICommunityService extends IBaseDao<Community> {
    public List<City> getCity(Short code);
}
