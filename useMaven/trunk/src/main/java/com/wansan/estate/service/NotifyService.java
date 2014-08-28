package com.wansan.estate.service;

import com.wansan.estate.model.Notice;
import com.wansan.estate.utils.EstateUtils;
import com.wansan.template.core.Utils;
import com.wansan.template.model.Ofuser;
import com.wansan.template.model.OperEnum;
import com.wansan.template.model.Person;
import com.wansan.template.model.Syslog;
import com.wansan.template.service.BaseDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 2014/8/11.
 */
@Service
public class NotifyService extends BaseDao<Notice> implements INotifyService {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private IBuildingService buildingService;
    @Override
    public Serializable txSave(Notice notice,Person person){
        String newID = (String)save(notice);
        switch (notice.getType()){
            case broadcast:
                try {
                    String returnStr = EstateUtils.callOfService("/messageservice","handleType=0&type=0&uuid="+newID);
                    logger.info(person.getName()+" send message "+returnStr);
                    logger.info("Message:"+returnStr);
                } catch (IOException e) {
                    throw new RuntimeException("通知发送失败！"+e.getMessage());
                }
                break;
            case specical:
                List<Ofuser> ofusers = buildingService.findUsersByBuilding(notice.getTo(),1,10);
                for(Ofuser ofuser:ofusers){
                    String reciver="&receiver="+ofuser.getUsername();
                    try {
                        EstateUtils.callOfService("/messageservice","handleType=0&type=2&uuid="+newID);
                        logger.info(person.getName()+" send to "+ofuser.getName());
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }
                break;
            case group:
                String returnStr = null;
                try {
                    returnStr = EstateUtils.callOfService("/messageservice", "handleType=0&type=1&uuid=" + newID);
                    logger.info(person.getName()+" send message "+returnStr);
                    logger.info("Message:"+returnStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }


        }
        Syslog syslog = new Syslog();
        syslog.setId(Utils.getNewUUID());
        syslog.setCreatetime(Utils.getNow());
        syslog.setUserid(person.getName());
        syslog.setName(OperEnum.CREATE.toString());
        syslog.setComment("发通知"+notice.getName());
        getSession().save(syslog);
        return newID;
    }
}
