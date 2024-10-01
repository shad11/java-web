package com.tinder.service;

import java.util.ArrayList;
import java.util.List;

import com.tinder.dao.UserDAO;
import com.tinder.dao.UserDaoImpl;
import com.tinder.exception.DataBaseException;
import com.tinder.exception.UserValidationException;
import com.tinder.model.User;
import com.tinder.util.PasswordHelper;

public class UserService {
    private final UserDAO userDAO = new UserDaoImpl();

    public User createUser(String email, String password) throws UserValidationException, DataBaseException {
        User user = userDAO.get(email);

        if (user != null) {
            throw new UserValidationException("User already registered!");
        }
        
        user = new User(email, PasswordHelper.hashPassword(password));

        userDAO.create(user);

        return user;
    }

    public User loginUser(String email, String password) throws UserValidationException, DataBaseException {
        User user = userDAO.get(email);

        if (user == null) {
            throw new UserValidationException("User not found");
        }

        if (!PasswordHelper.checkPassword(password, user.getPassword())) {
            throw new UserValidationException("Invalid password");
        }

        return user;
    }

    public User getUser(String email) throws DataBaseException {
        return userDAO.get(email);
    }

    public List<User> getAllUsers(User currentUser) throws DataBaseException {
        List<User> users = userDAO.getAll();

        if (currentUser != null) {
            users.removeIf(user -> user.getId() == currentUser.getId() || currentUser.getLikedUsers().contains(user.getId()));
        }

        return users;
    }

    public void likeUser(User user, int likedUserId) throws DataBaseException {
        List<Integer> likedUsers = new ArrayList<>(user.getLikedUsers());

        if (!likedUsers.contains(likedUserId)) {
            likedUsers.add(likedUserId);

            user.setLikedUsers(likedUsers);
            userDAO.update(user);
        }
    }

    public void dislikeUser(User user, int dislikedUserId) throws DataBaseException {
        List<Integer> likedUsers = new ArrayList<>(user.getLikedUsers());

        likedUsers.removeIf(id -> id == dislikedUserId);

        user.setLikedUsers(likedUsers);
        userDAO.update(user);
    }

    public List<User> getLikedUsers(User user) throws DataBaseException {
        List<User> likedUsers = new ArrayList<>();
        List<Integer> likedUsersIds = user.getLikedUsers();

        if (likedUsersIds.isEmpty()) {
            return likedUsers;
        }

        for (int id : likedUsersIds) {
            likedUsers.add(userDAO.get(id));
        }

        return likedUsers;
    }

    public void updateUser(User user) throws DataBaseException {
        userDAO.update(user);
    }
}
