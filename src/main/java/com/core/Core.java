package com.core;

import com.configs.ConfigConnection;
import com.database.DataBase;
import com.image.Image;

import java.sql.SQLException;

public class Core {

    ConfigConnection conf = new ConfigConnection();
    public static DataBaseConnectivity db;
    public static DataBase db1;
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
        try {
            db1 = new DataBase(url,login,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(db1.executeRequest("SELECT * FROM cards"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Core();
    }
}
