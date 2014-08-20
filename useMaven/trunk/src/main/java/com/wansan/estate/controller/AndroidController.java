package com.wansan.estate.controller;

import com.wansan.estate.model.AdCol;
import com.wansan.estate.model.AdContent;
import com.wansan.estate.model.NoticetypeEnum;
import com.wansan.estate.service.IAdcolService;
import com.wansan.estate.service.IAdcontentService;
import com.wansan.estate.service.IOfuserService;
import com.wansan.template.controller.BaseController;
import com.wansan.template.model.Ofuser;
import com.wansan.template.model.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-7-11.
 */
@Controller
@ResponseBody
@RequestMapping(value = "/android")
public class AndroidController extends BaseController {
    private final static String UploadPath = "/upload";

    @Resource
    private IOfuserService ofuserService;
    @Resource
    private IAdcontentService adcontentService;
    @Resource
    private IAdcolService adcolService;

    @RequestMapping(value = "/getOfuser")
    public List<Ofuser> getOfuser(String username,String roomName){
        return ofuserService.findByGate(username,roomName);
    }

    @RequestMapping(value = "/android/upload")
    public Map<String,Object> upload(MultipartFile file1,HttpServletRequest request){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getServletContext());
        Map<String,Object> result = new HashMap<>();
        if(resolver.isMultipart(request)){
            String name = file1.getOriginalFilename();
            String path = request.getServletContext().getRealPath(UploadPath);
            try {
                file1.transferTo(new File(path,name));
                result.put("status",ResultEnum.SUCCESS);
                result.put("msg",name);
            } catch (IOException e) {
                result.put("status",ResultEnum.FAIL);
                result.put("msg",e.getMessage());
            }
        }else {
            result.put("status",ResultEnum.FAIL);
            result.put("msg","Form type is incorrect!");
        }
        return result;
    }

    @RequestMapping(value = "/getAdList")
    public List<Map<String,Object>> getAdList(NoticetypeEnum noticetypeEnum){
        List<Map<String,Object>> result = new ArrayList<>();
        switch (noticetypeEnum){
            case adContent:
                List<AdContent> contentList = adcontentService.listAll();
                for(AdContent item:contentList){
                    Map<String,Object> itemMap = new HashMap<>();
                    itemMap.put("id",item.getId());
                    itemMap.put("createtime",item.getCreatetime());
                    result.add(itemMap);
                }
                break;
            case adcol:
                List<AdCol> colList = adcolService.listAll();
                for(AdCol item:colList){
                    Map<String,Object> itemMap = new HashMap<>();
                    itemMap.put("id",item.getId());
                    itemMap.put("createtime",item.getCreatetime());
                    result.add(itemMap);
                }
                break;
        }
        return result;
    }

    @RequestMapping(value = "/getAdItem")
    public AdContent getAdItem(String id){
        return adcontentService.findById(id);
    }

    @RequestMapping(value = "/getAdcol")
    public AdCol getAdcol(String id){
        return adcolService.findById(id);
    }
}
