package com.database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.ArrayList;

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

    //region Add (shop, owner, card)
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

    public void addOwner(String name, String surname, String patronymic, String passportNumber){
        String sql = "INSERT INTO owners(name, surname, patronymic, passport_number) VALUES (?, ?, ?, ?);";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,name);
            prSt.setString(2,surname);
            prSt.setString(3,patronymic);
            prSt.setString(4,passportNumber);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCard(String number,String owner,int shop ){
        String sql = "INSERT INTO cards(number, owner, shop) VALUES (?, ?, ?);";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,number);
            prSt.setString(2,owner);
            prSt.setInt(3,shop);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Delete (shop, owner, card)
    public void deleteShop(int id){
        String sql = "DELETE FROM shops WHERE id = ?;";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1,id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteShop(String name){
        String sql = "DELETE FROM shops WHERE name = ?;";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,name);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOwner(int number){
        String sql = "DELETE FROM owners WHERE passport_number = ?;";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1,number);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCard(int id){
        String sql = "DELETE FROM cards WHERE id = ?;";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1,id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Get shop
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

        JSONArray jsons = new JSONArray();
        jsons.add(json);
        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }

    public String getShop(String name){
        String sql = "SELECT * FROM shops WHERE name = ?;";
        String resultReturn = "";
        JSONObject json = new JSONObject();
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,name);
            resultSet = prSt.executeQuery();

            resultSet.next();
            json.put("id",resultSet.getString(1));
            json.put("shop",resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONArray jsons = new JSONArray();
        jsons.add(json);
        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }
    //endregion

    //region Get owners
    public String getOwners(String passportNumber){
        String sql = "SELECT * FROM owners WHERE passport_number = ?;";
        String resultReturn = "";
        JSONObject json = new JSONObject();
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,passportNumber);
            resultSet = prSt.executeQuery();

            resultSet.next();
            json.put("name",resultSet.getString(1));
            json.put("surname",resultSet.getString(2));
            json.put("patronymic",resultSet.getString(3));
            json.put("passportNumber",resultSet.getString(4));
            json.put("useCount",resultSet.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONArray jsons = new JSONArray();
        jsons.add(json);
        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }

    public String getOwners(String name, String surname, String patronymic){
        String sql = "SELECT * FROM owners WHERE name = ? AND surname = ? AND patronymic = ?;";
        JSONArray jsons = new JSONArray();
        String resultReturn = "";
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,name);
            prSt.setString(2,surname);
            prSt.setString(3,patronymic);
            resultSet = prSt.executeQuery();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                json.put("name", resultSet.getString(1));
                json.put("surname", resultSet.getString(2));
                json.put("patronymic", resultSet.getString(3));
                json.put("passportNumber", resultSet.getString(4));
                json.put("useCount", resultSet.getString(5));
                jsons.add(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }
    //endregion

    //region Get use count / increase use count
    public String getOwnersWithMinUse(){
        String sql = "SELECT * FROM owners WHERE use_count = (SELECT MIN (use_count) FROM owners);";
        JSONArray jsons = new JSONArray();
        String resultReturn = "";
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            resultSet = prSt.executeQuery();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                json.put("name", resultSet.getString(1));
                json.put("surname", resultSet.getString(2));
                json.put("patronymic", resultSet.getString(3));
                json.put("passportNumber", resultSet.getString(4));
                json.put("useCount", resultSet.getString(5));
                jsons.add(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }

    void increaseUseCount(String passportNumber){
        String sql = "UPDATE owners SET use_count=use_count+1 WHERE passport_number = ?";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,passportNumber);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Get cards list
    public String getOwnersCards(String passportNumber){
        String sql = "SELECT * FROM cards WHERE owner = ?;";
        JSONArray jsons = new JSONArray();
        String resultReturn = "";
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,passportNumber);
            resultSet = prSt.executeQuery();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                json.put("id", resultSet.getString(1));
                json.put("ownersNumber", resultSet.getString(2));
                json.put("owner", resultSet.getString(3));
                json.put("shop", resultSet.getString(4));
                jsons.add(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }
    //endregion

    public String getCard(int id, int idOrShop){
        String resultReturn = "";
        JSONObject json = new JSONObject();
        ResultSet resultSet;
        String sql = "";
        switch (idOrShop){
            case 1:
                sql = "SELECT * FROM cards WHERE id = ?;";
                break;
            case 2:
                sql = "SELECT * FROM cards WHERE shop = ?";
        }
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1,id);
            resultSet = prSt.executeQuery();

            resultSet.next();
            json.put("id",resultSet.getString(1));
            json.put("number",resultSet.getString(2));
            json.put("owner",resultSet.getString(3));
            json.put("shop",resultSet.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONArray jsons = new JSONArray();
        jsons.add(json);
        resultReturn = jsons.toJSONString();

        return resultReturn;
    }
}
