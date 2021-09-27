package com.database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DataBase {
    Connection conn;
    PreparedStatement prSt;

    public DataBase(String url, String user, String password) throws SQLException {
         conn = DriverManager.getConnection(url,user,password);
    }

    public String executeRequest(String sql) throws Exception {
        String requestType = sql.substring(0,sql.indexOf(" "));

        String returnResult = "";

        switch (requestType){
            case "SELECT":
                returnResult = executeResult(sql);
                break;
            case "INSERT":
            case "DELETE":
            case "UPDATE":
                executeAction(sql);
                break;
            default:
                throw new Exception("SQL command doesn't exist");
        }

        return  returnResult;
    }

    private String executeResult(String sql) throws SQLException {
        JSONArray jsons = new JSONArray();
        String resultReturn = "";
        ResultSet resultSet;
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

        resultReturn = jsons.toJSONString();

        return  resultReturn;
    }

    private void executeAction(String sql) throws SQLException {
        prSt = conn.prepareStatement(sql);
        prSt.executeUpdate();
    }
}
