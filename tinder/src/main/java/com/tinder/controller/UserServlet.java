package com.tinder.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinder.exception.UserValidationException;
import com.tinder.model.User;
import com.tinder.service.UserService;
import com.tinder.util.CookieHelper;
import com.tinder.util.TemplateEngine;

@WebServlet(urlPatterns = { "/login", "/register", "/profile", "/users" })
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();

        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();

        switch (path) {
            case "/login" -> {
                renderTemplate(response, "user/login.ftl", null);
            }
            case "/register" -> {
                renderTemplate(response, "user/register.ftl", null);
            }
            case "/users" -> {
                showUsers(request, response);
            }
            default -> {
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();

        switch (path) {
            case "/login" -> {
                loginUser(request, response);
            }
            case "/register" -> {
                registerUser(request, response);
            }
            case "/liked" -> {
                likeUser(request, response);
            }
            default -> {
                showUsers(request, response);
            }
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userService.loginUser(email, password);

            CookieHelper.setEmail(response, user.getEmail());

            // response.sendRedirect(request.getContextPath() + "/users");
            sendJsonResponse(response, "{\"success\": true, \"redirect\": \"/users\"}");
        } catch (UserValidationException e) {
            e.printStackTrace();

            sendJsonResponse(response, "{\"success\": false, \"msg\": \"" + e.getMessage() + "\"}");
        } catch (SQLException e) {
            e.printStackTrace();

            response.setStatus(500);
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {

            User user = userService.createUser(email, password);

            CookieHelper.setEmail(response, user.getEmail());

            // response.sendRedirect(request.getContextPath() + "/users");
            sendJsonResponse(response, "{\"success\": true, \"redirect\": \"/users\"}");
        } catch (UserValidationException e) {
            e.printStackTrace();

            sendJsonResponse(response, "{\"success\": false, \"msg\": \"" + e.getMessage() + "\"}");
        } catch (SQLException e) {
            e.printStackTrace();

            sendJsonResponse(response, "{\"success\": false, \"msg\": \"" + e.getMessage() + "\"}");
        }
    }

    private void showUsers(HttpServletRequest request, HttpServletResponse response) {
        try {
            String userEmail = CookieHelper.getEmail(request);
            List<User> users = userService.getAllUsers(userEmail);

            Map<String, Object> data = Map.of("users", users);

            renderTemplate(response, "user/users.ftl", data);
        } catch (Exception e) {
            e.printStackTrace();

            response.setStatus(500);
        }
    }

    private void likeUser(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String userEmail = CookieHelper.getEmail(request);

        try {
            User user = userService.getUser(userEmail);

            userService.likeUser(user, Integer.parseInt(userId));

            sendJsonResponse(response, "{\"success\": true}");
        } catch (SQLException e) {
            e.printStackTrace();

            sendJsonResponse(response, "{\"success\": false, \"msg\": \"" + e.getMessage() + "\"}");
        }
    }

    private void renderTemplate(HttpServletResponse response, String template, Map<String, Object> data) {
        if (data == null || data.isEmpty()) {
            TemplateEngine.render(response, template);
        } else {
            TemplateEngine.render(response, template, data);
        }
    }

    private void sendJsonResponse(HttpServletResponse response, String json) {
        response.setContentType("application/json");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();

            response.setStatus(500);
        }
    }
}
