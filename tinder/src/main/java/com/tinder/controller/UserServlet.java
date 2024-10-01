package com.tinder.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinder.exception.DataBaseException;
import com.tinder.exception.UserValidationException;
import com.tinder.model.User;
import com.tinder.service.UserService;
import com.tinder.util.CookieHelper;
import com.tinder.util.RequestHelper;
import com.tinder.util.ResponseHelper;
import com.tinder.util.TemplateEngine;
import com.tinder.util.ValidationHelper;

@WebServlet(urlPatterns = { "/login", "/register", "/logout", "/profile", "/users" })
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
                TemplateEngine.render(response, "login.ftl");
            }
            case "/register" -> {
                TemplateEngine.render(response, "register.ftl");
            }
            case "/users" -> {
                showUsers(request, response);
            }
            case "/liked" -> {
                showLikedUsers(request, response);
            }
            case "/logout" -> {
                logoutUser(request, response);
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
                processUserLikeDislike(request, response, true);
            }
            default -> {
                showUsers(request, response);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();

        switch (path) {
            case "/liked" -> {
                processUserLikeDislike(request, response, false);
            }
            default -> {}
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!ValidationHelper.isValidEmail(email)) {
            ResponseHelper.sendJsonResponse(response, "{\"success\": false, \"msg\": \"Invalid email format.\"}");
            return;
        }

        if (!ValidationHelper.isValidPassword(password)) {
            ResponseHelper.sendJsonResponse(response, "{\"success\": false, \"msg\": \"Password must be at least 8 characters long and contain letters and numbers.\"}");
            return;
        }

        try {
            User user = userService.loginUser(email, password);

            CookieHelper.setEmail(response, user.getEmail());

            // response.sendRedirect(request.getContextPath() + "/users");
            ResponseHelper.sendJsonResponse(response, "{\"success\": true, \"redirect\": \"/users\"}");
        } catch (UserValidationException e) {
            // e.printStackTrace();
            ResponseHelper.sendJsonResponse(response, "{\"success\": false, \"msg\": \"" + e.getMessage() + "\"}");
        } catch (DataBaseException e) {
            ResponseHelper.sendJsonResponse(response, "{\"success\": false, \"msg\": \"DB error...\"}");
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!ValidationHelper.isValidEmail(email)) {
            ResponseHelper.sendJsonResponse(response, "{\"success\": false, \"msg\": \"Invalid email format.\"}");
            return;
        }

        if (!ValidationHelper.isValidPassword(password)) {
            ResponseHelper.sendJsonResponse(response, "{\"success\": false, \"msg\": \"Password must be at least 8 characters long and contain letters and numbers.\"}");
            return;
        }

        try {
            User user = userService.createUser(email, password);

            CookieHelper.setEmail(response, user.getEmail());

            // response.sendRedirect(request.getContextPath() + "/users");
            ResponseHelper.sendJsonResponse(response, "{\"success\": true, \"redirect\": \"/users\"}");
        } catch (UserValidationException e) {
            // e.printStackTrace();
            ResponseHelper.sendJsonResponse(response, "{\"success\": false, \"msg\": \"" + e.getMessage() + "\"}");
        } catch (DataBaseException e) {
            ResponseHelper.sendJsonResponse(response, "{\"success\": false, \"msg\": \"DB error...\"}");
        }
    }

    private void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        CookieHelper.deleteEmail(response);

        try {
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showUsers(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getAttribute("user");
            List<User> users = userService.getAllUsers(user);

            Map<String, Object> data = Map.of("users", users);

            TemplateEngine.render(response, "users.ftl", data);
        } catch (DataBaseException e) {
            ResponseHelper.showErrorPage(response, "DB error...");
        } catch (Exception e) {
            e.printStackTrace();

            ResponseHelper.showErrorPage(response, e.getMessage());
        }
    }

    private void processUserLikeDislike(HttpServletRequest request, HttpServletResponse response, boolean like) {
        String userId = request.getParameter("userId");

        if (userId == null) {
            try {
                Map<String, String> params = RequestHelper.getParametersFromBody(request);

                userId = params.get("userId");
            } catch (IOException e) {
                e.printStackTrace();

                ResponseHelper.sendJsonResponse(response, "{\"success\": false, \"msg\": \"" + e.getMessage() + "\"}");
            }
        }

        if (userId == null) {
            ResponseHelper.sendJsonResponse(response, "{\"success\": false, \"msg\": \"Liked userId is required\"}");

            return;
        }

        try {
            User user = (User) request.getAttribute("user");

            if (like) {
                userService.likeUser(user, Integer.parseInt(userId));
            } else {
                userService.dislikeUser(user, Integer.parseInt(userId));
            }

            ResponseHelper.sendJsonResponse(response, "{\"success\": true}");
        } catch (DataBaseException e) {
            ResponseHelper.sendJsonResponse(response, "{\"success\": false, \"msg\": \"DB error...\"}");
        }
    }

    private void showLikedUsers(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getAttribute("user");

            List<User> likedUsers = userService.getLikedUsers(user);

            Map<String, Object> data = Map.of("users", likedUsers);

            TemplateEngine.render(response, "likedUsers.ftl", data);
        } catch (Exception e) {
            e.printStackTrace();

            ResponseHelper.showErrorPage(response, e.getMessage());
        }
    }
}
