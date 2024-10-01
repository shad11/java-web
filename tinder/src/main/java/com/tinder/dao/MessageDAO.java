package com.tinder.dao;

import java.util.List;

import com.tinder.exception.DataBaseException;
import com.tinder.model.Message;

public interface MessageDAO {
    void create(Message message) throws DataBaseException;
    List<Message> getAll(int senderId, int receiverId) throws DataBaseException;
}
