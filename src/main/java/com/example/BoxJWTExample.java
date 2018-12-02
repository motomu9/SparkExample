package com.example;

import com.box.sdk.*;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class BoxJWTExample {

  public static void mainBoxAPI() throws IOException {

    // Read config file into Box Config object
    Reader reader = new FileReader("D:\\Dev\\SparkExample\\src\\main\\resources\\config.json");
    BoxConfig boxConfig = BoxConfig.readFrom(reader);

    // Set cache info
    int MAX_CACHE_ENTRIES = 100;
    IAccessTokenCache accessTokenCache = new InMemoryLRUAccessTokenCache(MAX_CACHE_ENTRIES);

    // Create new app enterprise connection object
    BoxDeveloperEditionAPIConnection client =
        BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(boxConfig, accessTokenCache);

    // PERFORM ACTIONS WITH CLIENT

    // Set upload values
    String filePath = "D:\\Dev\\SparkExample\\upload\\boxapitest.txt";
    String fileName = "boxapitest.txt";
    String folderId = "59903117297";

    // Select Box folder
    BoxFolder folder = new BoxFolder(client, folderId);

    // Upload file
    FileInputStream stream = new FileInputStream(filePath);
    BoxFile.Info newFileInfo = folder.uploadFile(stream, fileName);
    stream.close();
  }

}
