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

    public DataBaseConnectivity(String url, String user, String password) {

        try {
            conn = DriverManager.getConnection(url,user,password);
            st = conn.createStatement();
            logger.info("Connection successful");
        } catch (SQLException e) {
            logger.error("Failed connect to data base: {}", e.toString());
        }
    }

    //region Add (shop, owner, card)
    public void addShop(String name) throws Exception {
        String sql = "INSERT INTO shops(name) VALUES (?);";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,name);
            prSt.executeUpdate();
            logger.trace("Shop was added");
        } catch (SQLException e) {
            logger.error("Can not add shop to data base: {}", e.toString());
            throw new Exception("serverException");
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
            logger.trace("Owner was added");
        } catch (SQLException e) {
            logger.error("Can not add owner to data base: {}", e.toString());
            throw new Exception("serverException");
        }
    }

    public void addCard(String number,String owner,int shop ) throws Exception {
        String sql = "INSERT INTO cards(number, owner, shop) VALUES (?, ?, ?);";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,number);
            prSt.setString(2,owner);
            prSt.setInt(3,shop);
            prSt.executeUpdate();
            logger.trace("Card was added");
        } catch (SQLException e) {
            logger.error("Can not add card to data base: {}", e.toString());
            throw new Exception("serverException");
        }
    }
    //endregion

    //region Delete (shop, owner, card)
    public void deleteShop(int id) throws Exception {
        String sql = "DELETE FROM shops WHERE id = ?;";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1,id);
            prSt.executeUpdate();
            logger.trace("Shop was deleted");
        } catch (SQLException e) {
            logger.error("Can not delete shop from data base: {}", e.toString());
            throw new Exception("serverException");
        }
    }

    public void deleteShop(String name) throws Exception {
        String sql = "DELETE FROM shops WHERE name = ?;";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,name);
            prSt.executeUpdate();
            logger.trace("Shop was deleted");
        } catch (SQLException e) {
            logger.error("Can not delete shop from data base: {}", e.toString());
            throw new Exception("serverException");
        }
    }

    public void deleteOwner(String number) throws Exception {
        String sql = "DELETE FROM owners WHERE passport_number = ?;";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,number);
            prSt.executeUpdate();
            logger.trace("Owner was deleted");
        } catch (SQLException e) {
            logger.error("Can not delete owner from data base: {}", e.toString());
            throw new Exception("serverException");
        }
    }

    public void deleteCard(int id) throws Exception {
        String sql = "DELETE FROM cards WHERE id = ?;";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1,id);
            prSt.executeUpdate();
            logger.trace("Card was deleted");
        } catch (SQLException e) {
            logger.error("Can not delete card from data base: {}", e.toString());
            throw new Exception("serverException");
        }
    }
    //endregion

    //region Get shop
    public String getShop(int id) throws Exception {
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
            logger.trace("Shop was received");
        } catch (SQLException e) {
            logger.error("Can not get shop from data base: {}", e.toString());
            throw new Exception("serverException");
        }

        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }

    public String getShop(String name) throws Exception {
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
            logger.trace("Shop was received");
        } catch (SQLException e) {
            logger.error("Can not get shop from data base: {}", e.toString());
            throw new Exception("serverException");
        }

        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }
    //endregion

    //region Get owners
    public String getOwners(String passportNumber) throws Exception {
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
            logger.trace("Owner was received");
        } catch (SQLException e) {
            logger.error("Can not get owner from data base: {}", e.toString());
            throw new Exception("serverException");
        }

        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }

    public String getOwners(String name, String surname, String patronymic) throws Exception {
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
            logger.trace("Owners was received");
        } catch (SQLException e) {
            logger.error("Can not get owner from data base: {}", e.toString());
            throw new Exception("serverException");
        }

        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }
    //endregion

    //region Get use count / increase use count
    public String getOwnersCardWithMinUse(int shop) throws Exception {
        String sql = "SELECT id,number,owner,shop FROM cards JOIN owners ON cards.owner = owners.passport_number WHERE shop = ? AND use_count = (SELECT MIN (use_count) FROM owners);";
        JSONArray jsons = new JSONArray();
        String resultReturn = "";
        ResultSet resultSet;
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setInt(1, shop);
            resultSet = prSt.executeQuery();

            ResultSetMetaData metaData = prSt.getMetaData();

            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for(int i = 0; i < metaData.getColumnCount(); i++) {
                    json.put(metaData.getColumnName(i+1),resultSet.getString(i+1));
                }
                jsons.add(json);
            }
            logger.trace("Owner's card with MIN use count was received");
        } catch (SQLException e) {
            logger.error("Can not get owner's card with use count from data base: {}", e.toString());
            throw new Exception("serverException");
        }

        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }

    void increaseUseCount(String passportNumber) throws Exception {
        String sql = "UPDATE owners SET use_count=use_count+1 WHERE passport_number = ?";
        try {
            prSt = conn.prepareStatement(sql);
            prSt.setString(1,passportNumber);
            prSt.executeUpdate();
            logger.trace("use count for owner was increased");
        } catch (SQLException e) {
            logger.error("Can not increase use count for owner from data base: {}", e.toString());
            throw new Exception("serverException");
        }
    }
    //endregion

    //region Get cards
    public String getCards(int id, int idOrShop) throws Exception {
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
            logger.trace("Cards was received");
        } catch (SQLException e) {
            logger.error("Can not get cads from data base: {}", e.toString());
            throw new Exception("serverException");
        }
        resultReturn = jsons.toJSONString();

        return resultReturn;
    }

    public String getCards(String number, int cardOrPassport) throws Exception {
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
            logger.trace("Cards was received");
        } catch (SQLException e) {
            logger.error("Can not get cards from data base: {}", e.toString());
            throw new Exception("serverException");
        }

        resultReturn = jsons.toJSONString();

        return resultReturn;
    }

    public String getCard(String number, int shop) throws Exception {
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
            logger.trace("Card was received");
        } catch (SQLException e) {
            logger.error("Can not get cards from data base: {}", e.toString());
            throw new Exception("serverException");
        }

        resultReturn = jsons.toJSONString();

        return resultReturn;
    }

    //endregion

    //region Get all
    public String getAllFromTable(String table) throws Exception {
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
            logger.trace(table + " was received");
        } catch (SQLException e) {
            logger.error("Can not get " + table + " from data base: {}", e.toString());
            throw new Exception("serverException");
        }

        resultReturn = jsons.toJSONString();

        return resultReturn;
    }
    //endregion
}
