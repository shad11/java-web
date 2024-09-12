package com.tinder.service;

import java.sql.SQLException;

import com.tinder.dao.UserDaoJDBC;
import com.tinder.model.User;

public class UserService {
    UserDaoJDBC userDAO = new UserDaoJDBC();

    public User createUser(String email, String password) {
        User user = null;

        try {
            user = new User(email, password);

            int id = userDAO.create(user);
            user.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User getUser(String email, String password) {
        User user = null;

        try {
            user = userDAO.get(email, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void updateUser(User user) {
        try {
            userDAO.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
