package com.example.db;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAccountDAO {

    private Sql2o sql2o;

    public UserAccountDAO() {
        this.sql2o = Sql2oConnection.getSql2o();

        Map<String, String> colMaps = new HashMap<>();
        colMaps.put("IntraAccount", "IntraAccount");
        colMaps.put("DeleteFlag", "DeleteFlag");
        sql2o.setDefaultColumnMappings(colMaps);
    }

    public List<UserAccount> getAll() {
        try (Connection con = sql2o.open()) {
            String queryText = "SELECT IntraAccount From UserAccount Where DeleteFlag = '0'";
            Query query = con.createQuery(queryText);
            return query.executeAndFetch(UserAccount.class);
        }
    }
}
