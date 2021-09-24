package com.core;

import com.configs.ConfigConnection;
import com.database.DataBaseConnectivity;
import com.image.Image;

public class Core {

    ConfigConnection conf = new ConfigConnection();
    public static DataBaseConnectivity db;
    public static Image image = new Image();

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

        image.getImagePath("123456789012",2);
    }

    public static void main(String[] args) {
        new Core();
    }
}
