import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";

    private static final String DB_PASSWORD = "alba77ww";

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test_db";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement1 = connection.createStatement();
        ResultSet result = statement1.executeQuery("select * from drivers");

        while (result.next()) {
            System.out.println(result.getInt("id") + " " +
                    result.getString("first_name"));
        }

        int countOfUsers = 1;
        System.out.println("Введите " + countOfUsers + " пользователей для добавления в бд: ");

        Scanner scannerOfUsers = new Scanner(System.in);
        List<String> users = new ArrayList<>();
        for (int i = 1; i <= countOfUsers; i++) {
            String user = scannerOfUsers.nextLine();
            users.add(user);
        }

        Statement statement2 = connection.createStatement();
        for (String user : users) {
            String[] userData = user.split(" ");
            String firstName = userData[0];
            String lastName = userData[1];
            int age = Integer.parseInt(userData[2]);

            String sql = "INSERT INTO drivers (first_name, last_name, age) VALUES (" +
                    "'" + firstName + "', " +
                    "'" + lastName + "', " +
                    age + ")";

            statement2.addBatch(sql);
        }

        int[] affectedRows1 = statement2.executeBatch();
        System.out.println("Успешно добавлено строк: " + affectedRows1.length);

        System.out.println("Введите возраст: ");
        Scanner scannerOfSortingAge = new Scanner(System.in);
        int sortingAge =  scannerOfSortingAge.nextInt();
        Statement statement3 = connection.createStatement();
        String sqlRequestForAgeSort = "SELECT * FROM drivers WHERE age = '" + sortingAge + "'";
        ResultSet usersWithExactAge = statement3.executeQuery(sqlRequestForAgeSort);

        if (usersWithExactAge.next()) {
            while (usersWithExactAge.next()) {
                System.out.println(usersWithExactAge.getString("first_name") + " " +
                        usersWithExactAge.getString("last_name"));
            }
        } else System.out.println("Таких пользователей в базе нет :( ");

        System.out.println("Введите одного пользователя для добавления в бд: ");
        Scanner scanner = new Scanner(System.in);
        String firstName = scanner.nextLine();
        String lastName = scanner.nextLine();
        int age = scanner.nextInt();

        String sqlInsertUser = "insert into drivers (first_name, last_name, age)" +
                "values (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setInt(3, age);

        int affectedRows2 = preparedStatement.executeUpdate();

        System.out.println("Было добалено " + affectedRows2 + " строк");
    }
}