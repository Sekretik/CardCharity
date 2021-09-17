package com.database;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Main {

    //String url = "jdbc:postgresql://localhost:5432/cardcharity";
    //String user = "postgres";
    //String password = "postgres";

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/cardcharity";
        String user = "postgres";
        String password = "postgres";

        DataBaseConnectivity db = new DataBaseConnectivity(url,user,password);

        db.addCard(1234567890,1,2);
    }
}
