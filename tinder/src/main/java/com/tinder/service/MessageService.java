package com.tinder.service;

import java.util.List;

import com.tinder.dao.MessageDAO;
import com.tinder.dao.MessageDaoImpl;
import com.tinder.exception.DataBaseException;
import com.tinder.model.Message;

public class MessageService {
    private final MessageDAO messageDAO = new MessageDaoImpl();

    public void createMessage(int senderId, int receiverId, String text) throws DataBaseException {
        Message message = new Message(senderId, receiverId, text);

        messageDAO.create(message);
    }

    public List<Message> getAllMessages(int senderId, int receiverId) throws DataBaseException {
        return messageDAO.getAll(senderId, receiverId);
    }
}
