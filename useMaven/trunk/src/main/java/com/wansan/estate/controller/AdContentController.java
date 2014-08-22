package com.wansan.estate.controller;

import com.wansan.estate.model.AdContent;
import com.wansan.estate.model.NoticetypeEnum;
import com.wansan.estate.service.IAdcontentService;
import com.wansan.estate.utils.EstateUtils;
import com.wansan.template.controller.BaseController;
import com.wansan.template.model.OperEnum;
import com.wansan.template.model.ResultEnum;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2014/8/19.
 */
@Controller
@RequestMapping(value = "/mainpage/admgr")
@ResponseBody
public class AdContentController extends BaseController {
    private Logger logger = Logger.getLogger(this.getClass());
    private final static String UploadPath = "/upload";
    @Resource
    private IAdcontentService adcontentService;
    
    @RequestMapping(value = "/addadcontent")
    public Map<String,Object> add(MultipartFile adpicture,HttpServletRequest request){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getServletContext());
        Map<String,Object> result = new HashMap<>();
        if(resolver.isMultipart(request)){
            String name = adpicture.getOriginalFilename();
            String nameWithoutExt = name.substring(0,name.indexOf("."));
            String ext = name.substring(name.indexOf("."));
            String smallFilename = nameWithoutExt+"_small"+ext;
            String path = request.getServletContext().getRealPath(UploadPath);
            try {
                File orgFile = new File(path,name);
                adpicture.transferTo(orgFile);
                Thumbnails.of(orgFile).size(120,100).toFile(new File(path,smallFilename));
            } catch (IOException e) {
                result.put("status",ResultEnum.FAIL);
                result.put("msg",e.getMessage());
                return result;
            }
            AdContent adContent = new AdContent();
            adContent.setName(request.getParameter("name"));
            adContent.setContent(request.getParameter("content"));
            adContent.setPicture(nameWithoutExt);
            adContent.setColID(request.getParameter("colId"));
            try {
                String newID = (String) adcontentService.txSave(adContent,getLoginPerson());
                EstateUtils.sendAdNotify(NoticetypeEnum.adContent,newID,OperEnum.CREATE);
                result.put("status",ResultEnum.SUCCESS);
                result.put("msg","添加成功！");
            }catch (Exception e){
                logger.error(e.getMessage());
                result.put("status",ResultEnum.FAIL);
                result.put("msg","操作失败，"+e.getMessage());
            }

        }else {
            result.put("status",ResultEnum.FAIL);
            result.put("msg","提交格式错误！");
        }
//        try {
//            String newID = (String) adcontentService.txSave(adContent,getLoginPerson());
//            EstateUtils.sendAdNotify(NoticetypeEnum.adContent,newID,OperEnum.CREATE);
//            return result(true);
//        }catch(Exception e){
//            logger.error(e.getMessage());
//            return result(false);
//        }
        return result;
    }
    
    @RequestMapping(value = "/updateadcontent")
    public Map<String,Object> update(AdContent adContent){
        try {
            adcontentService.txUpdate(adContent,getLoginPerson());
            EstateUtils.sendAdNotify(NoticetypeEnum.adContent,adContent.getId(), OperEnum.UPDATE);
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }
    
    @RequestMapping(value = "/deleteadcontent")
    public Map<String,Object> delete(String idList){
        try {
            adcontentService.txDelete(idList,getLoginPerson());
            for(String id:idList.split(","))
                EstateUtils.sendAdNotify(NoticetypeEnum.adContent,id,OperEnum.DELETE);
            return result(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result(false);
        }
    }
    
    @RequestMapping(value = "/listadcontent")
    public Map<String,Object> list(int page,int rows){
        Map<String,Object> result = adcontentService.findByMap(null,page,rows,"createtime",false);
        result.put("status", ResultEnum.SUCCESS);
        return result;
    }
}
