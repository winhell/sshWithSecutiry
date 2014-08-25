package com.wansan.estate.controller;

import com.wansan.estate.model.AdContent;
import com.wansan.estate.model.NoticetypeEnum;
import com.wansan.estate.service.IAdcontentService;
import com.wansan.estate.utils.EstateUtils;
import com.wansan.template.controller.BaseController;
import com.wansan.template.core.Utils;
import com.wansan.template.model.OperEnum;
import com.wansan.template.model.ResultEnum;
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
        String adType = request.getParameter("colID");
        Map<String,Object> result = new HashMap<>();
        if(resolver.isMultipart(request)&&null!=adpicture){

            String nameWithoutExt = Utils.getNewFilename();
            String ext = ".jpg";

            String path = request.getServletContext().getRealPath(UploadPath);
            try {
                File orgFile = new File(path,nameWithoutExt+ext);
                adpicture.transferTo(orgFile);
                if(null!=adType&&!"".equals(adType)) {
                    Thumbnails.of(orgFile).size(800,600).toFile(orgFile);
                    String smallFilename = nameWithoutExt + "_small" + ext;
                    Thumbnails.of(orgFile).size(120, 90).toFile(new File(path, smallFilename));
                }else
                    Thumbnails.of(orgFile).size(1280,720).toFile(orgFile);
            } catch (IOException e) {
                result.put("status",ResultEnum.FAIL);
                result.put("msg",e.getMessage());
                return result;
            }
            AdContent adContent = new AdContent();
            adContent.setName(request.getParameter("name"));
            adContent.setContent(request.getParameter("content"));
            adContent.setPicture(nameWithoutExt);
            adContent.setColID(adType);
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
            result.put("msg","提交格式错误，或图片不存在！");
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
