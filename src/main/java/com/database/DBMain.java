package com.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DBMain {

    DataBase db;
    private final Logger logger = LoggerFactory.getLogger(DataBase.class);

    public DBMain(String url, String user, String password) {
        try {
            db = new DataBase(url,user,password);
            logger.info("Connection to data base successful");
        } catch (SQLException e) {
            logger.error("Failed connect to data base: {}", e.toString());
        }
    }


    //region Get Card
    public String getCardWithId(int id){
        String sql = "SELECT * FROM cards WHERE id = " + id;
        String returnResult = "";
        try {
            returnResult = db.executeRequest(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  returnResult;
    }

    public String getCardsWithShopId(int id){
        String sql = "SELECT * FROM cards WHERE shop = " + id;
        String returnResult = "";
        try {
            returnResult = db.executeRequest(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  returnResult;
    }

    public String getCardsWithOwner(String owner){
        String sql = "SELECT * FROM cards WHERE owner = '" + owner + "'";
        String returnResult = "";
        try {
            returnResult = db.executeRequest(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  returnResult;
    }

    public String getCardsWithCardNumber(String number){
        String sql = "SELECT * FROM cards WHERE number = '" + number + "'";
        String returnResult = "";
        try {
            returnResult = db.executeRequest(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  returnResult;
    }

    public String getCardsWithCardNumberAndShopId(String number,int id){
        String sql = "SELECT * FROM cards WHERE number = '" + number + "' AND shop = " + id;
        String returnResult = "";
        try {
            returnResult = db.executeRequest(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  returnResult;
    }
    //endregion

    //region Get Owner
    public String getOwnerWithPassNumber(String owner){
        String sql = "SELECT * FROM owner WHERE passport_number = '" + owner + "'";
        String returnResult = "";
        try {
            returnResult = db.executeRequest(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  returnResult;
    }

    public String getOwnerWithFIO(String name, String surname, String patronymic){
        String sql = "SELECT * FROM owners WHERE name = '"+name+"' AND surname = '"+surname+"' AND patronymic = '"+patronymic+"'";
        String returnResult = "";
        try {
            returnResult = db.executeRequest(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  returnResult;
    }
    //endregion

    //region Get Shop
    public String getShopWithId(int id){
        String sql = "SELECT * FROM shops WHERE id = " + id;
        String returnResult = "";
        try {
            returnResult = db.executeRequest(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  returnResult;
    }

    public String geShopWithName(String name){
        String sql = "SELECT * FROM shops WHERE name = " + name;
        String returnResult = "";
        try {
            returnResult = db.executeRequest(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  returnResult;
    }
    //endregion

    //region Add
    public void addShop(String name){
        String sql = "INSERT INTO shops(name) VALUES ('"+name+"')";
        try {
            db.executeRequest(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOwner(String name, String surname, String patronymic, String passportNumber){
        String sql = "INSERT INTO owners(name,surname,patronymic,passport_number) VALUES('"+name+"','"+surname+"','"+patronymic+"',"+passportNumber+")";
        try {
            db.executeRequest(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCard(String number,String owner,int shop){
        String sql = "INSERT INTO cards(number, owner, shop) VALUES ('"+number+"','"+owner+"',"+shop+")";
    }
    //endregion

    //region Delete
    public void deleteShop(int id){
        String sql = "DELETE FROM shops WHERE id = "+ id;
        try {
            db.executeRequest(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOwner(String passportNumber){
        String sql = "DELETE FROM owners WHERE id = '"+ passportNumber+"'";
        try {
            db.executeRequest(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCard(String cardNumber){
        String sql = "DELETE FROM cards WHERE id = '"+ cardNumber+"'";
        try {
            db.executeRequest(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
