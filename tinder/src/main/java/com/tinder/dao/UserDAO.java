package com.tinder.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.tinder.model.User;
import com.tinder.util.DBConnector;

public interface UserDAO {
    int create(User user) throws SQLException;
    User get(String email) throws SQLException;
    User get(int id) throws SQLException;
    void update(User user) throws SQLException;
    void updateLikedUsers(User user, String likedUsers) throws SQLException;
    List<User> getAll() throws SQLException;

    default Connection getConnection() {
        return DBConnector.connect();
    }
}
