package com.example.util;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordUtil {

  /** パスワードを安全にするためのアルゴリズム */
  private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
  /** ストレッチング回数 */
  private static final int ITERATION_COUNT = 10000;
  /** 生成される鍵の長さ */
  private static final int KEY_LENGTH = 256;

  /**
   * 　平文のパスワードとソルトから安全なパスワードを生成し、返却します
   *
   * @param password 平文のパスワード
   * @param salt ソルト
   * @return 安全なパスワード
   */
  public static String getSafetyPassword(String password, String salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {

    char[] passCharAry = password.toCharArray();
    byte[] hashedSalt = getHashedSalt(salt);

    PBEKeySpec keySpec = new PBEKeySpec(passCharAry, hashedSalt, ITERATION_COUNT, KEY_LENGTH);

    SecretKeyFactory skf;
    skf = SecretKeyFactory.getInstance(ALGORITHM);

    SecretKey secretKey;
    secretKey = skf.generateSecret(keySpec);
    byte[] passByteAry = secretKey.getEncoded();

    // 生成されたバイト配列を16進数の文字列に変換
    StringBuilder sb = new StringBuilder(64);
    for (byte b : passByteAry) {
      sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
  }

  /**
   * ソルトをハッシュ化して返却します ※ハッシュアルゴリズムはSHA-256を使用
   *
   * @param salt ソルト
   * @return ハッシュ化されたバイト配列のソルト
   */
  private static byte[] getHashedSalt(String salt) throws NoSuchAlgorithmException {
    MessageDigest messageDigest;
    messageDigest = MessageDigest.getInstance("SHA-256");
    messageDigest.update(salt.getBytes());
    return messageDigest.digest();
  }

  public static boolean chkRequirement(String pw) {

    String regMark =
        "!\"#\\\\$%&'\\\\(\\\\)\\\\*\\\\+,\\\\.\\\\/<=>\\\\?@\\\\[\\\\\\\\\\\\]\\\\^_`\\\\{\\\\|\\\\}~\\-:;";

    if (!pw.matches("[\\d\\w" + regMark + "]+")) {
      System.out.println("使えない文字がある");
      return false;
    }
    if (!pw.matches(".{8,}")) {
      System.out.println("桁数が足りない");
      return false;
    }
    if (!pw.matches(".{8,16}")) {
      System.out.println("桁数が超過");
      return false;
    }

    return true;
  }
}
