package com.example;


import spark.QueryParamsMap;
import spark.Request;
import spark.Response;

public class AjaxServiceController {


    public String receiveForGetMethod(Request request, Response response) {
        System.out.println("Get method");
        return this.receive(request, response);
    }

    public String receiveForPostMethod(Request request, Response response) {
        System.out.println("Post method");
        return this.receive(request, response);
    }

    // ★注目★
    private String receive(Request request, Response response) {
        QueryParamsMap map = request.queryMap();
        try {
            String userId = map.get("userId").value();
            String userName = map.get("userName").value();

            String userId2 = request.queryParams("userId");
            String userName2 = request.queryParams("userName");
            return "userId : " + userId + " userName : " + userName +
                    " userId2 : " + userId2 + " userName2 : " + userName2;
        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }
    }
}
