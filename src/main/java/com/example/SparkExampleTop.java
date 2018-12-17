package com.example;

import com.example.db.UserAccountDAO;
import com.example.util.PasswordUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.QueryParamsMap;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static spark.utils.StringUtils.isNotEmpty;

public class SparkExampleTop {

  public static void main(String[] args) {

    port(8082);

    final Configuration configuration = new Configuration(new Version(2, 3, 0));
    configuration.setClassForTemplateLoading(SparkExampleTop.class, "/");

    get(
        "/testa",
        (request, response) -> {
          StringWriter writer = new StringWriter();

          try {
            freemarker.template.Template formTemplate =
                configuration.getTemplate("templates/form.ftl");

            formTemplate.process(null, writer);
          } catch (Exception e) {
            Spark.halt(500);
          }

          return writer;
        });

    Spark.post(
        "/sait",
        (request, response) -> {
          StringWriter writer = new StringWriter();

          try {
            String name =
                request.queryParams("name") != null ? request.queryParams("name") : "anonymous";
            String email =
                request.queryParams("email") != null ? request.queryParams("email") : "unknown";

            Template resultTemplate = configuration.getTemplate("templates/result.ftl");

            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("email", email);

            resultTemplate.process(map, writer);
          } catch (Exception e) {
            Spark.halt(500);
          }

          return writer;
        });
    get("/TabText", (request, response) -> TextProp.prop());

    get(
        "/file",
        (req, res) -> {
          System.out.println("ファイルをダウンロード");

          // ダウンロードファイルの作成
          String fileName = "資料.xlsx";

          // プロジェクトのroot/public/ フォルダが使用される。
          File file = new File("upload/" + fileName);
          String savePath = file.getAbsolutePath();

          System.out.println("ダウンロードするファイル=" + savePath);

          byte[] fileContent = null;
          try (InputStream is = new FileInputStream(savePath); ) {
            fileContent = IOUtils.toByteArray(is);
          } catch (FileNotFoundException e) {
            throw new RuntimeException(fileName + " not found");
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          // ファイル名の文字化け対策。
          String downName = URLEncoder.encode(fileName, "UTF-8");

          res.type("application/octet-stream");
          res.header("Content-Disposition", "attachment; filename=" + downName);
          res.raw().setContentLength(fileContent.length);

          try (OutputStream os = res.raw().getOutputStream(); ) {
            os.write(fileContent);
            os.flush();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          return "";
        });

    get(
        "/",
        (req, res) -> {
          StringWriter writer = new StringWriter();

          try {
            freemarker.template.Template formTemplate =
                configuration.getTemplate("templates/Login.ftl");

            formTemplate.process(null, writer);
          } catch (Exception e) {
            Spark.halt(500);
          }

          return writer;
        });

    post(
        "/login",
        (req, res) -> {
          StringWriter writer = new StringWriter();
          QueryParamsMap map = req.queryMap();
          String intraAccount = map.get("intraAccount").value();
          String password = map.get("password").value();
          String password2 = map.get("password2").value();

          if (isNotEmpty(password2)) {
            // Password2があれば登録
            if (!password.equals(password2)) {
              // パスワードの不一致
              System.out.println("パスワードの不一致");
              res.redirect("/");
            }

            if (!PasswordUtil.chkRequirement(password)) {
              // パスワードの要件不足
              System.out.println("パスワードの要件不足");
              res.redirect("/");
            }
          }
          String pwFromDB = new UserAccountDAO().getPassword(intraAccount);
          String pwFromScreen = PasswordUtil.getSafetyPassword(password, intraAccount);

          if (pwFromDB.isEmpty()) {
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("intraAccount", map.get("intraAccount").value());
            // パスワードが取れないので登録を促す
            freemarker.template.Template formTemplate =
                configuration.getTemplate("templates/registLogin.ftl");
            formTemplate.process(resMap, writer);
            return writer;
          }

          if (pwFromDB.equals(pwFromScreen)) {
            return "OK";
          } else {
            return "NG" + "\\n" + "screen is " + pwFromScreen + "\\n\\n" + "DB is " + pwFromDB;
          }
        });
  }
}
