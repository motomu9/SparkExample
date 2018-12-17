package com.example.db;

import java.io.Serializable;

public class UserAccount implements Serializable {
  public static final long serialVersionUID = 1L;

  private String intraAccount;

  private String password;
  private String deleteFlag;

  public UserAccount() {}

  public String getIntraAccount() {
    return intraAccount;
  }

  public void setIntraAccount(String intraAccount) {
    this.intraAccount = intraAccount;
  }

  public String getDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(String deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserAccount(String intraAccount,String password, String deleteFlag) {
    this.intraAccount = intraAccount;
    this.password = password;
    this.deleteFlag = deleteFlag;
  }

  @Override
  public String toString() {
    return "UserAccount{" +
            "intraAccount='" + intraAccount + '\'' +
            ", password='" + password + '\'' +
            ", deleteFlag='" + deleteFlag + '\'' +
            '}';
  }
}
