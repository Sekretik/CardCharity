package com.database;

public class Main {

    //String url = "jdbc:postgresql://localhost:5432/cardcharity";
    //String user = "postgres";
    //String password = "postgres";

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/cardcharity";
        String user = "postgres";
        String password = "postgres";

        DataBaseConnectivity db = new DataBaseConnectivity(url,user,password);

        System.out.println(db.getCard(1,2));
    }
}
