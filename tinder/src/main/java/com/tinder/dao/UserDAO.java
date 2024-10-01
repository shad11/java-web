package com.tinder.dao;

import java.util.List;

import com.tinder.exception.DataBaseException;
import com.tinder.model.User;

public interface UserDAO {
    void create(User user) throws DataBaseException;
    User get(String email) throws DataBaseException;
    User get(int id) throws DataBaseException;
    void update(User user) throws DataBaseException;
    void delete(int id) throws DataBaseException;
    List<User> getAll() throws DataBaseException;
}
