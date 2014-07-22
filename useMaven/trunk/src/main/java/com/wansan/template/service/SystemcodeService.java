package com.wansan.template.service;

import com.wansan.template.model.CodeEnum;
import com.wansan.template.model.Systemcode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-7-13.
 */
@Service
public class SystemcodeService extends BaseDao<Systemcode> implements ISystemcodeService {

    @Override
    public Map<Integer, String> getCodetypeList() {
        Map<Integer,String> result = new HashMap<>();
        for(CodeEnum c:CodeEnum.values()){
            result.put(c.ordinal(),c.toString());
        }
        return result;
    }

    @Override
    public String getCodeString(CodeEnum type, int code) {
        Map<String,Object> params = new HashMap<>();
        params.put("type",type);
        params.put("code",code);
        List<Systemcode> list = (List<Systemcode>) findByMap(params,0,0,null,false).get("rows");
        if(list.size()>0)
        return list.get(0).getName();
        else
            return "未定义";
    }

    @Override
    public int getCode(CodeEnum type, String codeName) {
        return 0;
    }

    @Override
    public Map<String, String> getCodeMap(CodeEnum type) {
        return null;
    }

    public List<Systemcode> getCodeList(CodeEnum type){
        return find("from Systemcode where type = ?",type);
    }
}
