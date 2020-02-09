package ru.kanaev.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertDataExample {

    public static void main(String[] args) {
        try (Connection connection = MySQLConnUtils.getMySQLConnection()) {
            Statement statement = connection.createStatement();

            String sql = "INSERT employee (emp_no, emp_name) "
                    + " values ('E2278', 'Viktor')";

            // Execute statement
            // executeUpdate(String) using for Insert, Update, Delete statement.
            int rowCount = statement.executeUpdate(sql);

            System.out.println("Row Count affected = " + rowCount);
            statement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
