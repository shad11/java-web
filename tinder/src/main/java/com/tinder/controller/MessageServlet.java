package com.tinder.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinder.exception.DataBaseException;
import com.tinder.model.Message;
import com.tinder.model.User;
import com.tinder.service.MessageService;
import com.tinder.util.ResponseHelper;
import com.tinder.util.TemplateEngine;

@WebServlet("/messages/*")
public class MessageServlet extends HttpServlet {
    private MessageService messageService;

    @Override
    public void init() {
        messageService = new MessageService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        int receiverId = getUserIdFromPath(request.getPathInfo(), response);

        if (receiverId == -1) {
            return;
        }

        User user = (User) request.getAttribute("user");

        if (user.getId() == receiverId) {
            ResponseHelper.showErrorPage(response, "You can't send messages to yourself.");

            return;
        }

        // Get messages for the user
        List<Message> messages;
        try {
            messages = messageService.getAllMessages(user.getId(), receiverId);

            Map<String, Object> data = Map.of(
                    "receiverId", receiverId,
                    "userId", user.getId(),
                    "messages", messages);

            TemplateEngine.render(response, "messages.ftl", data);
        } catch (DataBaseException e) {
            ResponseHelper.showErrorPage(response, "DB error...");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getAttribute("user");
        String msg = request.getParameter("message");

        int receiverId = getUserIdFromPath(request.getPathInfo(), response);

        if (receiverId == -1) {
            return;
        }

        try {
            messageService.createMessage(user.getId(), receiverId, msg);

            ResponseHelper.sendJsonResponse(response, "{\"success\": true}");
        } catch (DataBaseException e) {
            ResponseHelper.showErrorPage(response, "DB error...");
        }
    }

    private int getUserIdFromPath(String pathInfo, HttpServletResponse response) {
        if (pathInfo == null || pathInfo.equals("/")) {
            ResponseHelper.showErrorPage(response, "User ID is missing.");

            return -1;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length < 2) {
            ResponseHelper.showErrorPage(response, "Invalid User ID.");

            return -1;
        }

        try {
            return Integer.parseInt(splits[1]);
        } catch (NumberFormatException e) {
            ResponseHelper.showErrorPage(response, "User ID should be a number.");

            return -1;
        }
    }
}
