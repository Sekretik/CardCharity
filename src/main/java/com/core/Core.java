package com.core;

import com.configs.ConfigConnection;
import com.database.DataBaseConnectivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Core {

    ConfigConnection conf = new ConfigConnection();
    DataBaseConnectivity db;

    public Core(){
        String url = "";
        String login = "";
        String password = "";
        try {
            url = conf.getConfigValue("dbServerURL");
            login = conf.getConfigValue("dbLogin");
            password = conf.getConfigValue("dbPassword");
        } catch (Exception e) {
            e.printStackTrace();
        }

        db = new DataBaseConnectivity(url,login,password);
    }

    public static void main(String[] args) {
        new Core();
    }
}
