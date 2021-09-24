package com.core;

import com.configs.ConfigConnection;
import com.database.DataBaseConnectivity;
import com.web.WebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Core {

    ConfigConnection conf = new ConfigConnection();
    static DataBaseConnectivity db;

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

        Image.getImagePath("123456789012",1);
    }

    public static void main(String[] args) {
        Core core = new Core();
    }
}
