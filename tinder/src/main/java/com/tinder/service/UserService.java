package com.tinder.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tinder.dao.UserDaoJDBC;
import com.tinder.exception.UserValidationException;
import com.tinder.model.User;
import com.tinder.util.PasswordHelper;

public class UserService {
    UserDaoJDBC userDAO = new UserDaoJDBC();

    public User createUser(String email, String password) throws SQLException, UserValidationException {
        User user = null;

        user = userDAO.get(email);

        if (user != null) {
            throw new UserValidationException("User already registered!");
        }
        
        user = new User(email, PasswordHelper.hashPassword(password));

        int id = userDAO.create(user);
        user.setId(id);

        return user;
    }

    public User loginUser(String email, String password) throws SQLException, UserValidationException {
        User user = userDAO.get(email);

        if (user == null) {
            throw new UserValidationException("User not found");
        }

        if (!PasswordHelper.checkPassword(password, user.getPassword())) {
            throw new UserValidationException("Invalid password");
        }

        return user;
    }

    public User getUser(String email) throws SQLException {
        return userDAO.get(email);
    }

    public List<User> getAllUsers(String userEmail) throws SQLException {
        List<User> users = userDAO.getAll();

        if (userEmail != null) {
            users.removeIf(user -> user.getEmail().equals(userEmail));
        }

        return users;
    }

    public void likeUser(User user, int likedUserId) throws SQLException {
        List<Integer> likedUsers = new ArrayList<>(user.getLikedUsers());

        if (!likedUsers.contains(likedUserId)) {
            likedUsers.add(likedUserId);

            String likedUsersString = likedUsers.stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(","));

            userDAO.updateLikedUsers(user, likedUsersString);
            
            user.setLikedUsers(likedUsers);
        }
    }

    public void updateUser(User user) throws SQLException {
        userDAO.update(user);
    }
}
