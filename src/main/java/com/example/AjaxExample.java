package com.example;

import com.google.gson.Gson;
import spark.Service;

public class AjaxExample {

    public static void main(String[] args) {
        AjaxExample sparkService = new AjaxExample();
        sparkService.initialize();
    }

    private Service service;

    private AjaxExample() {
        this.service = Service.ignite();
    }

    public void initialize() {
        this.service.staticFiles.location("/WebContents");

        Gson gson = new Gson();
        AjaxServiceController controller = new AjaxServiceController();

        // ★注目★
        this.service.get("/forGetMethod", controller::receiveForGetMethod);
        this.service.post("/forPostMethod", controller::receiveForPostMethod);

        this.service.get("/stop", (request, response) -> {
            // Server stop!
            this.service.stop();
            return null;
        });
    }
}
