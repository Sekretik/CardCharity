package com.core;

import com.configs.ConfigConnection;
import com.database.DBMain;
import com.database.DataBase;
import com.image.Image;

import java.sql.SQLException;

public class Core {

    ConfigConnection conf = new ConfigConnection();
    public static DBMain db;
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

        db = new DBMain(url,login,password);

        System.out.println(db.getOwnersCardWithMinUse(1));
    }

    public static void main(String[] args) {
        new Core();
    }
}
