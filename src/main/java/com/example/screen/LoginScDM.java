package com.example.screen;

/**
 * ログイン画面のDataModelクラス
 */
public class LoginScDM extends DataModleBase {

    /**
     * MAP KEY : ID
     */
    public static final String KEY_ID = "id";

    /**
     * IDを設定する
     */
    public void setId(String id) {
        this.put(KEY_ID, id);
    }
}
