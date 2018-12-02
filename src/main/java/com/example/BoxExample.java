package com.example;

import static spark.Spark.get;
import static spark.Spark.port;

public class BoxExample {

  public static void main(String[] args) {

    port(8083);

    get("/", (req, res) -> "Test");

    get(
        "/box",
        (request, response) -> {
          BoxJWTExample.mainBoxAPI();
          return "OK";
        });
  }
}
