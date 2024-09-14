package com.tinder.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinder.model.User;
import com.tinder.service.UserService;
import com.tinder.util.FreemarkerUtil;

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
                renderTemplate(response, "user/login.ftl");
            }
            case "/register" -> {
                renderTemplate(response, "user/register.ftl");
            }
            case "/profile" -> {
                renderTemplate(response, "user/profile.ftl");
            }
            case "/users" -> {
                renderTemplate(response, "user/users.ftl");
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
            default -> {
                renderTemplate(response, "user/users.ftl");
            }
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user;

            user = userService.getUser(email, password);

            if (user != null) {
                // TODO: Save into Cookie / Session
                // response.sendRedirect(request.getContextPath() + "/users");
                sendJsonResponse(response, "{\"success\": true, \"redirect\": \"/users\"}");
            } else {
                user = userService.getUser(email); 
                
                if (user != null) {
                    sendJsonResponse(response, "{\"success\": false, \"msg\": \"Invalid password\"}");
                } else {
                    sendJsonResponse(response, "{\"success\": false, \"msg\": \"User not found\"}");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

            response.setStatus(500);
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {

            User user;
            user = userService.getUser(email);

            if (user != null) {
                sendJsonResponse(response, "{\"success\": false, \"msg\": \"User already registered\"}");
                return;
            }

            user = userService.createUser(email, password);

            if (user != null) {
                // TODO: Save into Cookie / Session
                // request.getSession().setAttribute("user", user);
                // response.sendRedirect(request.getContextPath() + "/users");
                sendJsonResponse(response, "{\"success\": true, \"redirect\": \"/users\"}");
            } else {
                sendJsonResponse(response, "{\"success\": false, \"msg\": \"User not registered\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();

            sendJsonResponse(response, "{\"success\": false, \"msg\": " + e.getMessage() + "}");
        }
    }

    private void renderTemplate(HttpServletResponse response, String template) {
        FreemarkerUtil.render(response, template);
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
