package com.wansan.estate.controller;

import com.wansan.template.controller.BaseController;
import com.wansan.template.core.Utils;
import com.wansan.template.model.Person;
import com.wansan.template.model.ResultEnum;
import com.wansan.template.service.IPersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 14-7-11.
 */
@Controller
@ResponseBody
public class AndroidController extends BaseController {
    @Resource
    private IPersonService personService;

    private final static String UploadPath = "/upload";
    @RequestMapping(value = "/android/login")
    public Map<String,Object> login(String username,String password){
        Map<String,Object> result = new HashMap<>();
        Person person = personService.findPersonByName(username);
        if(null == person){
            result.put("status",ResultEnum.FAIL);
            result.put("msg","用户未找到!");
        }else {
            String encrptyPw = Utils.encodePassword(password,username);
            if(!encrptyPw.equals(person.getPassword())){
                result.put("status",ResultEnum.FAIL);
                result.put("msg","用户密码错误！");
            }else {
                result.put("status", ResultEnum.SUCCESS);
                result.put("msg","用户登录成功！");
            }
        }
        return result;
    }

    @RequestMapping(value = "/android/upload")
    public Map<String,Object> upload(HttpServletRequest request) throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        Map<String,Object> result = new HashMap<>();
        if(resolver.isMultipart(request)){
            MultipartRequest multipartRequest = (MultipartRequest)request;
            MultipartFile file = multipartRequest.getFile("filename");
            String name = request.getParameter("username");
            String path = request.getServletContext().getRealPath(UploadPath);
            file.transferTo(new File(path,name));
            result.put("status",ResultEnum.SUCCESS);
            result.put("msg",name);
        }else {
            result.put("status",ResultEnum.FAIL);
            result.put("msg","Form type is incorrect!");
        }
        return result;

    }
}
