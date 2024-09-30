package com.tinder.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinder.model.User;
import com.tinder.service.UserService;
import com.tinder.util.CookieHelper;

@WebFilter("/*") // Apply the filter to all paths
public class AuthenticationFilter implements Filter {
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

        userService = new UserService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String email;

        String path = req.getRequestURI();

        if (path.equals("/login") || path.equals("/register")) {
            if (CookieHelper.getEmail(req) != null) {
                res.sendRedirect(req.getContextPath() + "/users");
            } else {
                chain.doFilter(request, response);
            }
        } else if (path.startsWith("/static")) {
            chain.doFilter(request, response);
        } else if ((email = CookieHelper.getEmail(req)) != null) {
            try {
                User user = userService.getUser(email);

                if (user == null) {
                    res.sendRedirect(req.getContextPath() + "/login");
                } else {
                    req.setAttribute("user", user);
                    chain.doFilter(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();

                res.sendRedirect(req.getContextPath() + "/login");
            }
        } else {
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
