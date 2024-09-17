package com.tinder.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.tinder.model.User;
import com.tinder.util.DBConnector;

public interface UserDAO {
    public int create(User user) throws SQLException;
    public User get(String email) throws SQLException;
    public void update(User user) throws SQLException;
    public void updateLikedUsers(User user, String likedUsers) throws SQLException;
    public List<User> getAll() throws SQLException;

    default Connection getConnection() {
        return DBConnector.connect();
    }
}
