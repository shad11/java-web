package com.tinder.service;

import com.tinder.dao.MessageDAO;
import com.tinder.dao.MessagesDaoJDBC;
import com.tinder.model.Message;
import java.sql.SQLException;
import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO = new MessagesDaoJDBC();

    public void createMessage(int senderId, int receiverId, String text) throws SQLException {
        Message message = new Message(senderId, receiverId, text);

        messageDAO.create(message);
    }

    public List<Message> getAllMessages(int senderId, int receiverId) throws SQLException {
        return messageDAO.getAll(senderId, receiverId);
    }
}
