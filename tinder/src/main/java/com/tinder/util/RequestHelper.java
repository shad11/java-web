package com.tinder.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
    public static Map<String, String> getParametersFromBody(HttpServletRequest request) throws IOException {
        Map<String, String> params = new HashMap<>();
        BufferedReader reader = request.getReader();
        String requestBody = "";
        String line;


        while ((line = reader.readLine()) != null) {
            requestBody += line;
        }

        if (requestBody.isEmpty()) {
            return params;
        }

        String[] pairs = requestBody.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            params.put(keyValue[0], keyValue[1]);
        }

        return params;
    }
}
