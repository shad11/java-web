package com.tinder.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ResponseHelper {
    public static void sendJsonResponse(HttpServletResponse response, String json) {
        response.setContentType("application/json");

        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();

            showErrorPage(response, e.getMessage());
        }
    }

    public static void showErrorPage(HttpServletResponse response, String message) {
        Map<String, Object> data = Map.of("message", message);

        TemplateEngine.render(response, "error.ftl", data);
    }
}
