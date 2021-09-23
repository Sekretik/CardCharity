package com.database;

import com.configs.ConfigMain;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DataBaseConnectivity {

    Connection conn;
    Statement st;
    PreparedStatement prSt;
    private final Logger logger = LoggerFactory.getLogger(DataBaseConnectivity.class);

    public DataBaseConnectivity(String url, String user, String password){

        try {
            conn = DriverManager.getConnection(url,user,password);
            st = conn.createStatement();
            logger.info("Connection successful");
        } catch (SQLException e) {
            logger.error("Failed connect to data base: {}", e.toString());
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

    public void addOwner(String name, String surname, String patronymic, String passportNumber) throws Exception {
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
            throw new Exception("serverException");
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
        JSONArray jsons = new JSONArray();
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1,id);
            resultSet = prSt.executeQuery();

            ResultSetMetaData metaData = prSt.getMetaData();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for(int i = 0; i < metaData.getColumnCount(); i++) {
                    json.put(metaData.getColumnName(i+1),resultSet.getString(i+1));
                }
                jsons.add(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }

    public String getShop(String name){
        String sql = "SELECT * FROM shops WHERE name = ?;";
        String resultReturn = "";
        JSONArray jsons = new JSONArray();
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,name);
            resultSet = prSt.executeQuery();

            ResultSetMetaData metaData = prSt.getMetaData();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for(int i = 0; i < metaData.getColumnCount(); i++) {
                    json.put(metaData.getColumnName(i+1),resultSet.getString(i+1));
                }
                jsons.add(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }
    //endregion

    //region Get owners
    public String getOwners(String passportNumber){
        String sql = "SELECT * FROM owners WHERE passport_number = ?;";
        String resultReturn = "";
        JSONArray jsons = new JSONArray();
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,passportNumber);
            resultSet = prSt.executeQuery();

            ResultSetMetaData metaData = prSt.getMetaData();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for(int i = 0; i < metaData.getColumnCount(); i++) {
                    json.put(metaData.getColumnName(i+1),resultSet.getString(i+1));
                }
                jsons.add(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

            ResultSetMetaData metaData = prSt.getMetaData();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for(int i = 0; i < metaData.getColumnCount(); i++) {
                    json.put(metaData.getColumnName(i+1),resultSet.getString(i+1));
                }
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

            ResultSetMetaData metaData = prSt.getMetaData();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for(int i = 0; i < metaData.getColumnCount(); i++) {
                    json.put(metaData.getColumnName(i+1),resultSet.getString(i+1));
                }
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

    //region Get cards
    public String getCards(int id, int idOrShop){
        String resultReturn = "";
        ResultSet resultSet;
        JSONArray jsons = new JSONArray();
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

            ResultSetMetaData metaData = prSt.getMetaData();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for(int i = 0; i < metaData.getColumnCount(); i++) {
                    json.put(metaData.getColumnName(i+1),resultSet.getString(i+1));
                }
                jsons.add(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resultReturn = jsons.toJSONString();

        return resultReturn;
    }

    public String getCards(String number, int cardOrPassport){
        String resultReturn = "";
        JSONArray jsons = new JSONArray();
        ResultSet resultSet;
        String sql = "";
        switch (cardOrPassport){
            case 1:
                sql = "SELECT * FROM cards WHERE number = ?;";
                break;
            case 2:
                sql = "SELECT * FROM cards WHERE owner = ?";
        }
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,number);
            resultSet = prSt.executeQuery();

            ResultSetMetaData metaData = prSt.getMetaData();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for(int i = 0; i < metaData.getColumnCount(); i++) {
                    json.put(metaData.getColumnName(i+1),resultSet.getString(i+1));
                }
                jsons.add(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultReturn = jsons.toJSONString();

        return resultReturn;
    }

    public String getCard(String number, int shop){
        String resultReturn = "";
        JSONArray jsons = new JSONArray();
        ResultSet resultSet;
        String sql = "SELECT * FROM cards WHERE number = ? AND shop = ?";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,number);
            prSt.setInt(2,shop);
            resultSet = prSt.executeQuery();

            ResultSetMetaData metaData = prSt.getMetaData();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for(int i = 0; i < metaData.getColumnCount(); i++) {
                    json.put(metaData.getColumnName(i+1),resultSet.getString(i+1));
                }
                jsons.add(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultReturn = jsons.toJSONString();

        return resultReturn;
    }

    //endregion

    //region Get all
    public String getAllFromTable(String table){
        String resultReturn = "";
        String sql = "SELECT * FROM " + table +";";
        JSONArray jsons = new JSONArray();
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            resultSet = prSt.executeQuery();

            ResultSetMetaData metaData = prSt.getMetaData();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for(int i = 0; i < metaData.getColumnCount(); i++) {
                    json.put(metaData.getColumnName(i+1),resultSet.getString(i+1));
                }
                jsons.add(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultReturn = jsons.toJSONString();

        return resultReturn;
    }
    //endregion
}
