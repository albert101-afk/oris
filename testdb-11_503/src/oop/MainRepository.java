package oop;

import oop.entities.User;
import oop.repositories.UserRepository;
import oop.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";

    private static final String DB_PASSWORD = "alba77ww";

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test_db";

    public static void main(String[] args) throws SQLException {
        // DriverManager - базовая служба для управления набором драйверов JDBC.
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);

        List<User> users = userRepository.findAll();

        users.forEach(user -> System.out.println(user.getName()));
    }
}
