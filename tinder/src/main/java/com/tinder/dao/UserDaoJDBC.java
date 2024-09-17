package com.tinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.tinder.model.User;

public class UserDaoJDBC implements UserDAO {
    @Override
    public int create(User user) throws SQLException {
        int id = 0;
        Connection connection = getConnection();

        String query = "INSERT INTO users (email, password) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());

        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            id = resultSet.getInt(1);
        } else {
            resultSet.close();
            preparedStatement.close();

            throw new SQLException("Creating user failed, no ID obtained.");
        }

        resultSet.close();
        preparedStatement.close();

        return id;
    }

    @Override
    public User get(String email) throws SQLException {
        User user = null;
        Connection connection = getConnection();

        String query = "SELECT * FROM users WHERE email = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            user = getUserFromResultSet(resultSet);
        }

        resultSet.close();
        preparedStatement.close();

        return user;
    }

    @Override
    public void update(User user) throws SQLException {
        Connection connection = getConnection();

        String query = "UPDATE users SET email = ?, password = ?, nick = ?, imgLink = ?, likedUsers = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getNick());
        preparedStatement.setString(4, user.getImgLink());
        preparedStatement.setString(5, user.getLikedUsers().toString());
        preparedStatement.setInt(6, user.getId());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void updateLikedUsers(User user, String likedUsers) throws SQLException {
        Connection connection = getConnection();

        String query = "UPDATE users SET likedUsers = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, likedUsers);
        preparedStatement.setInt(2, user.getId());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public List<User> getAll() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        Connection connection = getConnection();

        String query = "SELECT * FROM users";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            User user = getUserFromResultSet(resultSet);

            users.add(user);
        }

        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        String[] likedUsers = Optional
            .ofNullable(resultSet.getString("likedUsers"))
            .orElse("")
            .split(",");
        
        return new User(
            resultSet.getInt("id"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("nick"),
            resultSet.getString("imgLink"),
            Arrays.stream(likedUsers).filter(linkedUser -> !linkedUser.isEmpty()).map(Integer::parseInt).toList()
        );
    }
}
