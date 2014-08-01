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
    public void test() throws Exception {
        String wholename = buildingService.getBuildingName("user7");
        Assert.assertNotNull(wholename);
    }

}