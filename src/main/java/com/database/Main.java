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

        String[] s = db.getOwnersCards(123);
        for (String ss : s) {
            System.out.println(ss);
        }
    }
}
