package com.core;

import com.configs.ConfigConnection;
import com.database.DBMain;
import com.database.DataBase;
import com.image.Image;
import com.web.WebServer;

import java.sql.SQLException;

public class Core {

    ConfigConnection conf = new ConfigConnection();
    public static DBMain db;
    public static Image image = new Image();

    public Core(){
        String url = "";
        String login = "";
        String password = "";
        String ip = "";
        int port = 0;
        try {
            url = conf.getConfigValue("dbServerURL");
            login = conf.getConfigValue("dbLogin");
            password = conf.getConfigValue("dbPassword");
            ip = conf.getConfigValue("webServerIP");
            port = Integer.parseInt(conf.getConfigValue("webServerPort"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        db = new DBMain(url,login,password);

        WebServer webServer = new WebServer(ip, port, HandlerMaker.makeHandlerCollection());
        webServer.startServer();

    }

    public static void main(String[] args) {
        new Core();
    }
}
