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
                String msg = notice.getName()+notice.getContent();
                try {
                    String returnStr = EstateUtils.callOfService("/messageservice","type=0&message="+msg);
                    logger.info(person.getName()+" send message "+returnStr);
                    logger.info("Message:"+msg);
                } catch (IOException e) {
                    throw new RuntimeException();
                }
                break;
            case specical:
                List<Ofuser> ofusers = buildingService.findUsersByBuilding(notice.getTo(),1,10);
                for(Ofuser ofuser:ofusers){
                    String reciver="&receiver="+ofuser.getUsername();
                    try {
                        EstateUtils.callOfService("/messageservice","type=1&message="+notice.getName()+reciver);
                        logger.info(person.getName()+" send to "+ofuser.getName());
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }
                break;

        }
        Syslog syslog = new Syslog();
        syslog.setId(Utils.getNewUUID());
        syslog.setCreatetime(Utils.getNow());
        syslog.setUserid(person.getName());
        syslog.setName(OperEnum.CREATE.toString());
        syslog.setComment("发通知"+notice.getName());
        return newID;
    }
}
