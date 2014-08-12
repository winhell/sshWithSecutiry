package com.wansan.estate.utils;

import com.wansan.template.model.ResultEnum;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2014/8/8.
 */
public class EstateUtils {
    public static String getOfSetting(String prop){
        return OfPropLoader.properties.getProperty(prop);
    }
    public static String callOfService(String func,String queryString) throws IOException {
        URL url = new URL(EstateUtils.getOfSetting("serverUrl") +
                getOfSetting("pluginPath") + func + "?" +
                "certificate=" + getOfSetting("authUser") + "&password=" + getOfSetting("authPass") +"&"+
                queryString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);

        if(connection.getResponseCode()==200)
            return IOUtils.toString(connection.getInputStream(), "UTF-8");
        return ResultEnum.FAIL.toString();
    }
}
