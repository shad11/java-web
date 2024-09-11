package com.tinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tinder.model.User;

public class UserDAO {
    Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public int create(User user) throws SQLException {
        int id = 0;
        String query = "INSERT INTO users (email, password) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());

        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }

        resultSet.close();
        preparedStatement.close();

        return id;
    }

    public User get(String email, String password) throws SQLException {
        User user = null;
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("nick"), 
                    resultSet.getString("img_link")
                );
        }

        resultSet.close();
        preparedStatement.close();

        return user;
    }

    public void update(User user) throws SQLException {
        String query = "UPDATE users SET email = ?, password = ?, nick = ?, img_link = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getNick());
        preparedStatement.setString(4, user.getImgLink());
        preparedStatement.setInt(5, user.getId());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
