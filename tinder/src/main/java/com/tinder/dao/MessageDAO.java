package com.tinder.dao;

import com.tinder.model.Message;
import com.tinder.util.DBConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {
    void create(Message message) throws SQLException;
    List<Message> getAll(int senderId, int receiverId) throws SQLException;

    default Connection getConnection() {
        return DBConnector.connect();
    }
}
