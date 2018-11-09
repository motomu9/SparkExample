package com.example.db;

import org.sql2o.Sql2o;

public class Sql2oConnection {

    private static Sql2o dbCon = new Sql2o("jdbc:sqlserver://localhost:1433", "TEST_USER", "TEST");

    private Sql2oConnection() {
    }

    public static Sql2o getSql2o() {
        return dbCon;
    }

}
