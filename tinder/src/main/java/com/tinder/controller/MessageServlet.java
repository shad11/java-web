package com.tinder.controller;

import com.tinder.model.Message;
import com.tinder.model.User;
import com.tinder.service.MessageService;
import com.tinder.util.ResponseHelper;
import com.tinder.util.TemplateEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/messages/*")
public class MessageServlet extends HttpServlet {
    // Default messages for users
    private final Map<Integer, String> messagesDefault = new HashMap<>(){{
        put(1, "Привіт!");
        put(2, "Привіт! Як справи?");
    }};
    private MessageService messageService;

    @Override
    public void init() {
        messageService = new MessageService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        int receiverId = getUserIdFromPath(req.getPathInfo(), resp);

        if (receiverId == -1) {
            return;
        }

        User user = (User) req.getAttribute("user");

        if (user.getId() == receiverId) {
            ResponseHelper.showErrorPage(resp, "You can't send messages to yourself.");

            return;
        }

        // Get messages for the user
        List<Message> messages;

        try {
            messages = messageService.getAllMessages(user.getId(), receiverId);
        } catch (SQLException e) {
            ResponseHelper.showErrorPage(resp, e.getMessage());

            return;
        }

        if (messages.isEmpty()) {
            String defaultMsg = messagesDefault.get(1 + new Random().nextInt(2));
            Message message = new Message(receiverId, user.getId(), defaultMsg);

            try {
                messageService.createMessage(receiverId, user.getId(), defaultMsg);
            } catch (SQLException e) {
                ResponseHelper.showErrorPage(resp, e.getMessage());

                return;
            }

            messages.add(message);
        }

        Map<String, Object> data = Map.of(
                "receiverId", receiverId,
                "userId", user.getId(),
                "messages", messages
        );

        TemplateEngine.render(resp, "messages.ftl", data);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getAttribute("user");
        String msg = req.getParameter("message");

        int receiverId = getUserIdFromPath(req.getPathInfo(), resp);

        if (receiverId == -1) {
            return;
        }

        try {
            messageService.createMessage(user.getId(), receiverId, msg);

            ResponseHelper.sendJsonResponse(resp, "{\"success\": true}");
        } catch (SQLException e) {
            e.printStackTrace();

            ResponseHelper.sendJsonResponse(resp, "{\"success\": false, \"msg\": \"" + e.getMessage() + "\"}");
        }
    }

    private int getUserIdFromPath(String pathInfo, HttpServletResponse resp) {
        if (pathInfo == null || pathInfo.equals("/")) {
            ResponseHelper.showErrorPage(resp, "User ID is missing.");

            return -1;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length < 2) {
            ResponseHelper.showErrorPage(resp, "Invalid User ID.");

            return -1;
        }

        try {
            return Integer.parseInt(splits[1]);
        } catch (NumberFormatException e) {
            ResponseHelper.showErrorPage(resp, "User ID should be a number.");

            return -1;
        }
    }
}
