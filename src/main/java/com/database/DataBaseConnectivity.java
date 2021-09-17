package com.database;

import netscape.javascript.JSObject;
import org.json.simple.JSONObject;

import java.sql.*;

public class DataBaseConnectivity {

    Connection conn;
    Statement st;
    PreparedStatement prSt;

    public DataBaseConnectivity(String url, String user, String password){
        try {
            conn = DriverManager.getConnection(url,user,password);
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addShop(String name){
        String sql = "INSERT INTO shops(name) VALUES (?);";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,name);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOwner(String name, String surname, String patronymic, int pasportNumber){
        String sql = "INSERT INTO owners(name, surname, patronymic, pasport_number) VALUES (?, ?, ?, ?);";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,name);
            prSt.setString(2,surname);
            prSt.setString(3,patronymic);
            prSt.setInt(4,pasportNumber);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCard(int number,int owner,int shop ){
        String sql = "INSERT INTO cards(number, owner, shop) VALUES (?, ?, ?);";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1,number);
            prSt.setInt(2,owner);
            prSt.setInt(3,shop);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getShop(int id){
        String sql = "SELECT * FROM shops WHERE id = ?;";
        String resultReturn = "";
        JSONObject json = new JSONObject();
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1,id);
            resultSet = prSt.executeQuery();

            resultSet.next();
            json.put("id",resultSet.getString(1));
            json.put("shop",resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resultReturn = json.toJSONString();

        return  resultReturn;
    }
}
