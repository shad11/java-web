package com.tinder.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHelper {
    // Encrypt the password
    public static String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

    // Check if the raw password matches the hashed password
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
