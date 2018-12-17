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
    colMaps.put("Password", "Password");
    colMaps.put("DeleteFlag", "DeleteFlag");
    sql2o.setDefaultColumnMappings(colMaps);
  }

  public List<UserAccount> getAll() {
    try (Connection con = sql2o.open()) {
      String queryText = "SELECT IntraAccount, Password From UserAccount Where DeleteFlag = '0'";
      Query query = con.createQuery(queryText);
      return query.executeAndFetch(UserAccount.class);
    }
  }

  public String getPassword(String intraAccount) {
    try (Connection con = sql2o.open()) {
      String queryText =
          "SELECT Password From UserAccount Where IntraAccount = :IntraAccount And DeleteFlag = '0'";
      Query query = con.createQuery(queryText).addParameter("IntraAccount", intraAccount);
      return query.executeAndFetchFirst(UserAccount.class).getPassword();
    }
  }

  public UserAccount getUserData(String intraAccount) {
    try (Connection con = sql2o.open()) {
      String queryText =
          "SELECT [IntraAccount], [Password], [DeleteFlag] From  [dbo].[UserAccount] Where [IntraAccount] =:IntraAccount and [DeleteFlag] = '0'";
      Query query = con.createQuery(queryText).addParameter("IntraAccount", intraAccount);
      return query.executeAndFetchFirst(UserAccount.class);
    }
  }

  public int insertUserData(UserAccount dto) {
    try (Connection con = sql2o.open()) {
      String queryText =
          "INSERT "
              + "INTO [dbo].[UserAccount] ([IntraAccount], [Password], [DeleteFlag]) "
              + "VALUES (:IntraAccount, :Password, :DeleteFlag)";
      Query query =
          con.createQuery(queryText)
              .addParameter("IntraAccount", dto.getIntraAccount())
              .addParameter("Password", dto.getPassword())
              .addParameter("DeleteFlag", dto.getDeleteFlag());
      return query.executeUpdate().getResult();
    }
  }

  public int updateUserData(UserAccount dto) {
    try (Connection con = sql2o.open()) {
      String queryText =
          "UPDATE [dbo].[UserAccount] "
              + "SET"
              + "  [Password] = :Password"
              + "  , [DeleteFlag] = :DeleteFlag "
              + "WHERE"
              + "  [IntraAccount] = :IntraAccount";
      Query query =
          con.createQuery(queryText)
              .addParameter("IntraAccount", dto.getIntraAccount())
              .addParameter("Password", dto.getPassword())
              .addParameter("DeleteFlag", dto.getDeleteFlag());
      return query.executeUpdate().getResult();
    }
  }
}
