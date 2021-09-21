package com.database;

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

    public void addOwner(String name, String surname, String patronymic, int pasportNumber){
        String sql = "INSERT INTO owners(name, surname, patronymic, passport_number) VALUES (?, ?, ?, ?);";
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
        resultReturn = json.toJSONString();

        return  resultReturn;
    }
    //endregion

    //region Get owners
    public String getOwners(int passportNumber){
        String sql = "SELECT * FROM owners WHERE pasport_number = ?;";
        String resultReturn = "";
        JSONObject json = new JSONObject();
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1,passportNumber);
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
        resultReturn = json.toJSONString();

        return  resultReturn;
    }

    public String[] getOwners(String name, String surname, String patronymic){
        String sql = "SELECT * FROM owners WHERE name = ? AND surname = ? AND patronymic = ?;";
        ArrayList<JSONObject> jsons = new ArrayList<>();
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

        String[] resultReturn = new String[jsons.size()];

        for (int i = 0; i < jsons.size(); i++) {
            resultReturn[i] = jsons.get(i).toJSONString();
        }

        return  resultReturn;
    }
    //endregion

    //region Get cards list
    public String[] getOwnersCards(int passportNumber){
        String sql = "SELECT * FROM cards WHERE owner = ?;";
        ArrayList<JSONObject> jsons = new ArrayList<>();
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1,passportNumber);
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

        String[] resultReturn = new String[jsons.size()];

        for (int i = 0; i < jsons.size(); i++) {
            resultReturn[i] = jsons.get(i).toJSONString();
        }

        return  resultReturn;
    }
    //endregion
}
