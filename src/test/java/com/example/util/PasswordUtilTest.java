package com.example.util;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

  @Test
  void getSafetyPassword() throws InvalidKeySpecException, NoSuchAlgorithmException {

    String pw1 = PasswordUtil.getSafetyPassword("abc", "123");
    String pw2 = PasswordUtil.getSafetyPassword("abc", "123");
    String pw3 = PasswordUtil.getSafetyPassword("abc", "456");

    assertEquals(pw1, pw2);
    assertNotEquals(pw1, pw3);
  }

  @Test
  void chkRequirement() {

    // 使用不可文字
    assertFalse(PasswordUtil.chkRequirement("あ"));

    // 文字数不足
    assertFalse(PasswordUtil.chkRequirement("a-@"));

    // 文字数超過
    assertFalse(PasswordUtil.chkRequirement("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));

    // 文字数超過
    assertFalse(PasswordUtil.chkRequirement("!\"#$%&'()=~|\\-^|@`[{;+:*]},./<>?_"));

    // OK
    assertTrue(PasswordUtil.chkRequirement("$%09m.o27&'"));
  }
}
