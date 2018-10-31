package com.example.screen;

import java.util.HashMap;

public class DataModleBase extends HashMap<String, String> {

    public static final String KEY_MESSAGE = "msg";

    public DataModleBase() {
        // 必ずメッセージを初期化する
        this.put(KEY_MESSAGE, "");
    }

    public void setMsg(String msg) {
        this.put(KEY_MESSAGE, msg);
    }
}


