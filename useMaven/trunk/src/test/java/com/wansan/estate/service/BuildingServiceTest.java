package com.wansan.estate.service;

import com.wansan.estate.model.Building;
import com.wansan.template.test.BaseTest;
import junit.framework.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class BuildingServiceTest extends BaseTest {

    @Resource
    private IBuildingService buildingService;

    @Test
    public void testTxSave() throws Exception {
        Building building = new Building();
        building.setName("test");
        building.setParent("582c6dff-14c0-11e4-adb4-7815febebb70");
        buildingService.txSave(building,null);
    }

    @Test
    public void testQuery(){
        List result = buildingService.getBuildingTree("c849a291-48d7-4199-a5bf-d32b5878abaf");
        Assert.assertEquals(3,result.size());
    }
}