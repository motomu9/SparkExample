package com.example;

import com.example.enums.UploadFileType;
import com.example.logic.UploadExcelController;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static spark.Spark.*;

public class UploadExample {

  public static void main(String[] args) {

    port(8081);

    File uploadDir = new File("upload");
    uploadDir.mkdir(); // create the upload directory if it doesn't exist

    staticFiles.externalLocation("upload");
    staticFiles.location("/WebContents");

    get(
        "/",
        (req, res) ->
            "<!DOCTYPE html>"
                + "<html>"
                + ""
                + "<head>"
                + "    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>"
                + "    <title>Hello World</title>"
                + "    <script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>"
                + "    <script src='./html/js/customAjax.js' type='text/javascript'></script>"
                + "</head>"
                + ""
                + "<body>"
                + "    <form> <input type='file' name='Master_File1' accept='.xlsx'> <button type='button' id='bt_Master_File1'>UploadExcelController</button> </form> "
                + "    Result: <span id='result_Master_File1'></span>"
                + "    <form> <input type='file' name='Master_File2' accept='.xlsx'> <button type='button' id='bt_Master_File2'>UploadExcelController</button> </form>"
                + "    <p> Result: <span id='result_Master_File2'></span> </p>"
                + "    <form> <input type='file' name='Master_File3' accept='.xlsx'> <button type='button' id='bt_Master_File3'>UploadExcelController</button> </form>"
                + "    <p> Result: <span id='result_Master_File3'></span> </p>"
                + "    <form> <input type='file' name='Tran_File1' accept='.xlsx'> <button type='button' id='bt_Tran_File1'>UploadExcelController</button> </form>"
                + "    <p> Result: <span id='result_Tran_File1'></span> </p>"
                + "    <form> <input type='file' name='Tran_File2' accept='.xlsx'> <button type='button' id='bt_Tran_File2'>UploadExcelController</button> </form>"
                + "    <p> Result: <span id='result_Tran_File2'></span> </p>"
                + "</body>"
                + ""
                + "</html>");

    post(
        "/fileUpload",
        (req, res) -> {
          Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

          req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

          Part part = null;
          UploadFileType fileType = null;

          for (UploadFileType type : UploadFileType.values()) {
              if (req.raw().getPart(type.name()) != null) {
                  part = req.raw().getPart(type.name());
                  fileType = type;
                  break;
              }
          }

          try (InputStream input =
              part.getInputStream()) { // getPart needs to use same "name" as input field in form
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
          }

          new UploadExcelController()
              .importExcelFile(fileType, tempFile.toAbsolutePath().toString());

          return fileType + "Processing";
        });
  }

  private static String getFileName(Part part) {
    for (String cd : part.getHeader("content-disposition").split(";")) {
      if (cd.trim().startsWith("filename")) {
        return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
      }
    }
    return null;
  }
}
