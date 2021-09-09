package com.database;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Main {

    public static void main(String[] args) {
        new DataBaseConnectivity();
        
        for (int i = 0;i < DataBaseConnectivity.getStringCount("cards");i++) {
            String data = DataBaseConnectivity.selectAllWhereId("cards", i+1);
            JSONObject json = (JSONObject) JSONValue.parse(data);
            System.out.println(json.toString());
        }
    }
}
