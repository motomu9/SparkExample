package com.example;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.port;

public class SparkExampleTop {

    public static void main(String[] args) {

        port(8082);

        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(SparkExampleTop.class, "/");

        Spark.get("/", (request, response) -> {

            StringWriter writer = new StringWriter();

            try {
                Template formTemplate = configuration.getTemplate("templates/form.ftl");

                formTemplate.process(null, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }

            return writer;
        });

        Spark.post("/sait", (request, response) -> {
            StringWriter writer = new StringWriter();

            try {
                String name = request.queryParams("name") != null ? request.queryParams("name") : "anonymous";
                String email = request.queryParams("email") != null ? request.queryParams("email") : "unknown";

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
    }
}
