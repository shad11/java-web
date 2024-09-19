package com.tinder.util;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {
    private static final String EMAIL_COOKIE = "tinder_email";

    public static void setCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);

        // cookie.setPath("/"); // Cookie is accessible from all paths
        cookie.setMaxAge(60 * 60 * 24 * 7); // Cookie will last for 7 days
        response.addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }
        
        return Arrays.stream(cookies)
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .map(Object::toString)
                .orElse(null);
    }

    public static void deleteCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, null);

        // cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void setEmail(HttpServletResponse response, String email) {
        setCookie(response, EMAIL_COOKIE, email);
    }

    public static String getEmail(HttpServletRequest request) {
        return getCookieValue(request, EMAIL_COOKIE);
    }

    public static void deleteEmail(HttpServletResponse response) {
        deleteCookie(response, EMAIL_COOKIE);
    }
}
