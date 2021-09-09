package com.database;

import netscape.javascript.JSObject;
import org.json.simple.JSONObject;

import java.sql.*;

public class DataBaseConnectivity {
    String url = "jdbc:postgresql://localhost:5432/cardcharity";
    String user = "postgres";
    String password = "postgres";

    static Connection conn;
    static Statement st;

    public DataBaseConnectivity(){
        try {
            conn = DriverManager.getConnection(url,user,password);
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String selectAllWhereId(String table,int id){
        String resultReturn = "";

        String sql = "SELECT * FROM " + table + " WHERE id = " + id;
        JSONObject json = new JSONObject();

        try {
            ResultSet resultSet = st.executeQuery(sql);
            String columns[] = getColumns(resultSet);

            while (resultSet.next()){
                for (String s:columns) {
                    json.put(s,resultSet.getString(s));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultReturn = json.toJSONString();

        return resultReturn;
    }

    static int getStringCount(String table){
        String sql = "SELECT * FROM " + table;
        int count = 0;
        try {
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    static void addCard(int number,int owner,int shop){
        try {
            String sql = "INSERT INTO cards(\"number\", \"owner\", \"shop\") VALUES('"+ number
                    +"','"+owner+"','"+shop +"')";
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addShop(String name){
        try {
            String sql = "INSERT INTO shops(name) VALUES('"+ name+"')";
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static void addOwner(String fName,String lName,String mName){
        try {
            String sql = "INSERT INTO owners(\"firstName\", \"lastName\", \"middleName\")" +
                    " VALUES('"+ fName
                    +"','"+lName+"','"+mName +"')";
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static String[] getColumns(ResultSet resultSet){
        ResultSetMetaData meta = null;
        int columnsCount = 0;

        try {
            meta = resultSet.getMetaData();
            columnsCount = meta.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String columns[] = new String[columnsCount];

        for (int i = 0; i < columnsCount; i++){
            try {
                columns[i] = meta.getColumnName(i+1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return  columns;
    }
}
