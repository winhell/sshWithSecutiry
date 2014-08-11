package com.wansan.estate.utils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2014/8/8.
 */
@Component
public class OfPropLoader {
    Logger logger = Logger.getLogger(this.getClass());

    public static Properties properties=new Properties();
    @PostConstruct
    void init(){
        logger.info("now loading properties of openfire....");
        InputStream in = this.getClass().getResourceAsStream("/openfire.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("openfire server is " + properties.getProperty("serverUrl"));
    }
}
