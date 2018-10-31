package com.example.db;

import java.io.Serializable;

public class UserAccount implements Serializable {
    public static final long serialVersionUID = 1L;

    private String intraAccount;
    private char deleteFlag;

    public UserAccount() {
    }

    public String getIntraAccount() {
        return intraAccount;
    }

    public void setIntraAccount(String intraAccount) {
        this.intraAccount = intraAccount;
    }

    public char getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(char deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public UserAccount(String intraAccount, char deleteFlag) {
        this.intraAccount = intraAccount;
        this.deleteFlag = deleteFlag;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "intraAccount='" + intraAccount + '\'' +
                ", deleteFlag=" + deleteFlag +
                '}';
    }

}
