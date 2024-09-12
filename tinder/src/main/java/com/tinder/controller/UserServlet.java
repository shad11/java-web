package com.tinder.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinder.util.FreemarkerUtils;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();
        String template = null;

        switch (path) {
            case "/login":
                template = "user/login.ftl";
                break;
            case "/register":
                template = "user/register.ftl";
                break;
            case "/profile":
                template = "user/profile.ftl";
                break;
            case "/users":
                template = "user/users.ftl";
                break;
            default:
                break;
        }
        
        renderTemplate(response, template);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();
        String template = null;

        switch (path) {
            case "/login":
                template = "user/login.ftl";
                break;
            case "/register":
                template = "user/register.ftl";
                break;
            case "/profile":
                template = "user/profile.ftl";
                break;
            case "/users":
                template = "user/users.ftl";
                break;
            default:
                break;
        }
        
        renderTemplate(response, template);
    }

    private void renderTemplate(HttpServletResponse response, String template) {
        FreemarkerUtils.render(response, template);
    }
}
