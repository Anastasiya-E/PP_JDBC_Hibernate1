package jm.task.core.jdbc.util;
import java.sql.*;

public class Util {
    private static final String HOST = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "rroot";
    private static final String PASSWORD = "rroot";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            System.out.println("Соединение установлено");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка соединения");
        }
        return connection;
    }
}
