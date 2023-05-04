package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = getConnection();

    public UserDaoJDBCImpl() throws SQLException {}

    public void createUsersTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
            CREATE TABLE IF NOT EXISTS USER (
              id INT NOT NULL AUTO_INCREMENT,
              name VARCHAR(45) NULL,
              lastName VARCHAR(45) NULL,
              age INT NULL,
              PRIMARY KEY (id));""")) {
            statement.execute();
        }
    }

    public void dropUsersTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS USER;")) {
            statement.execute();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO USER (name, lastName, age) VALUES (?, ?, ?);")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.execute();
        }
        System.out.println("Пользователь с именем " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM USER WHERE id = ?;")) {
            statement.setLong(1, id);
            statement.execute();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT id, name, lastName, age FROM USER")) {
            List<User> userList = new ArrayList<>();
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                User user = new User();
                user.setId(res.getLong("id"));
                user.setName(res.getString("name"));
                user.setLastName(res.getString("lastName"));
                user.setAge((byte) res.getInt("age"));
                userList.add(user);
            }
            return userList;
        }
    }

    public void cleanUsersTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE USER;")) {
            statement.execute();
        }
    }
}
