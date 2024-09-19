package com.tinder.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinder.util.CookieHelper;

@WebFilter("/*") // Apply the filter to all paths
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        if (path.equals("/login") || path.equals("/register")) {
            if (CookieHelper.getEmail(req) != null) {
                res.sendRedirect(req.getContextPath() + "/users");
            } else {
                chain.doFilter(request, response);
            }
        } else if (path.startsWith("/static")) {
            chain.doFilter(request, response);
        } else if (CookieHelper.getEmail(req) != null) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
