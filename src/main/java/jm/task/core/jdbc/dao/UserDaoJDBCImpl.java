package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = getConnection();
    private final Statement statement = connection.createStatement();

    public UserDaoJDBCImpl() throws SQLException {

    }

    public void createUsersTable() throws SQLException {
        statement.execute("""
                CREATE TABLE IF NOT EXISTS `USER` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NULL,
                  `lastName` VARCHAR(45) NULL,
                  `age` INT NULL,
                  PRIMARY KEY (`id`));""");
    }

    public void dropUsersTable() throws SQLException {
        statement.execute("DROP TABLE IF EXISTS USER;");
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        statement.execute("INSERT INTO USER (name, lastName, age) VALUES ('" + name + "', '" + lastName + "', " + age + ");");
        System.out.println("Пользователь с именем " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) throws SQLException {
        statement.execute("DELETE FROM USER WHERE id = id;");
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        ResultSet res = statement.executeQuery("SELECT id, name, lastName, age FROM USER");
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

    public void cleanUsersTable() throws SQLException {
        statement.execute("TRUNCATE TABLE USER;");
    }
}
