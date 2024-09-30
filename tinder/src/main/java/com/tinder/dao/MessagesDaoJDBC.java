package com.tinder.dao;

import com.tinder.model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessagesDaoJDBC implements MessageDAO {
    @Override
    public void create(Message message) throws SQLException {
        Connection connection = getConnection();

        String query = "INSERT INTO messages (senderId, receiverId, message) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, message.getSenderId());
        preparedStatement.setInt(2, message.getReceiverId());
        preparedStatement.setString(3,message.getMessage());

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    @Override
    public List<Message> getAll(int senderId, int receiverId) throws SQLException {
        ArrayList<Message> messages = new ArrayList<>();
        Connection connection = getConnection();

        String query = "SELECT * FROM messages WHERE (senderId = ? AND receiverId = ?) OR (senderId = ? AND receiverId = ?) ORDER BY timestamp";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, senderId);
        preparedStatement.setInt(2, receiverId);
        preparedStatement.setInt(3, receiverId);
        preparedStatement.setInt(4, senderId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Message message = new Message(
                    resultSet.getInt("id"),
                    resultSet.getInt("senderId"),
                    resultSet.getInt("receiverId"),
                    resultSet.getString("message"),
                    resultSet.getString("timestamp")
            );

            messages.add(message);
        }

        resultSet.close();
        preparedStatement.close();

        return messages;
    }
}
