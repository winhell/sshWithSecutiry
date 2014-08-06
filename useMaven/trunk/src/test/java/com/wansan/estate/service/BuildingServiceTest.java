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
        String wholename = buildingService.getBuildingName("727b6431-de6d-4727-ba88-1de6110e3d9f");
        Assert.assertNotNull(wholename);
    }

}