package com.example;

import com.example.enums.UploadFileType;
import com.example.logic.UploadExcelController;
import com.google.gson.Gson;
import spark.Request;
import spark.Service;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class App {
  public static void main(String[] args) {
    App sparkService = new App();
    sparkService.initialize();
  }

  private Service service;

  private App() {
    this.service = Service.ignite();
  }

  public void initialize() {
    File uploadDir = new File("upload");
    uploadDir.mkdir(); // create the upload directory if it doesn't exist
    this.service.staticFiles.location("/WebContents");
    this.service.staticFiles.externalLocation("upload");
    Gson gson = new Gson();
    AjaxServiceController controller = new AjaxServiceController();

    this.service.get(
        "/",
        (req, res) ->
            "<form method='post' enctype='multipart/form-data'>" // note the enctype
                + "    <input type='file' name='uploaded_file' accept='.xlsx'>" // make sure to call
                                                                                // getPart using the
                                                                                // same "name" in
                                                                                // the post
                + "    <button>UploadExcelController</button>"
                + "</form>");

    this.service.post(
        "/upload",
        (req, res) -> {
          Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

          req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

          try (InputStream input =
              req.raw()
                  .getPart("uploaded_file")
                  .getInputStream()) { // getPart needs to use same "name" as input field in form
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
          }

          logInfo(req, tempFile);

          // FIXME ファイルタイプは固定になっている
          UploadFileType fileType = UploadFileType.Master_File1;
          new UploadExcelController()
              .importExcelFile(fileType, tempFile.toAbsolutePath().toString());

          return "<h1>You uploaded this image:</h1><src='" + tempFile.getFileName() + "'>";
        });

    // ★注目★
    this.service.get("/forGetMethod", controller::receiveForGetMethod);
    this.service.post("/forPostMethod", controller::receiveForPostMethod);

    this.service.get(
        "/stop",
        (request, response) -> {
          // Server stop!
          this.service.stop();
          return null;
        });
  }
  // methods used for logging
  private static void logInfo(Request req, Path tempFile) throws IOException, ServletException {
    System.out.println(
        "Uploaded file '"
            + getFileName(req.raw().getPart("uploaded_file"))
            + "' saved as '"
            + tempFile.toAbsolutePath()
            + "'");
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
