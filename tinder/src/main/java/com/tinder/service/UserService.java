package com.tinder.service;

import java.sql.SQLException;

import com.tinder.dao.UserDaoJDBC;
import com.tinder.model.User;

public class UserService {
    UserDaoJDBC userDAO = new UserDaoJDBC();

    public User createUser(String email, String password) throws SQLException {
        User user = null;
        
        user = new User(email, password);

        int id = userDAO.create(user);
        user.setId(id);

        return user;
    }

    public User getUser(String email, String password) throws SQLException {
        return userDAO.get(email, password);
    }

    public void updateUser(User user) throws SQLException {
        userDAO.update(user);
    }
}
