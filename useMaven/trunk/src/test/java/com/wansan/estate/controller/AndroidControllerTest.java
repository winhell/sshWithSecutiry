package com.wansan.estate.controller;

import com.wansan.estate.model.NoticetypeEnum;
import com.wansan.template.test.BaseMVCTest;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class AndroidControllerTest extends BaseMVCTest{
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private AndroidController androidController;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(androidController).build();
    }
    @Test
    public void testGetAdList() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/android/getAdList.action?noticetypeEnum=adcol"))
                .andReturn();
        log.info(result.getResponse().getContentAsString());
    }
}