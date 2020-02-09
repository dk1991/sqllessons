package ru.kanaev.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScrollableResultSetExample {
    private static final String SELECT_ALL = "SELECT * FROM simplehr.employee";

    public static void main(String[] args) {

        try (Connection connection = MySQLConnUtils.getMySQLConnection()) {

            // Create a Statement object
            // can be scrolled, but not sensitive to changes under DB.
            // ResultSet is readonly (Cannot update)
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            // Jump cursor to last record
            boolean last = resultSet.last();

            System.out.println("last: " + last);

            if (last) {
                System.out.println("Id: " + resultSet.getInt("Emp_id"));
                System.out.println("No: " + resultSet.getString("Emp_No"));
                System.out.println("Name: " + resultSet.getString("Emp_Name"));
            }

            System.out.println("--------------------");

            // Move cursor to previous record
            boolean previous = resultSet.previous();
            System.out.println("Previous 1: " + previous);

            // Move cursor to previous record
            previous = resultSet.previous();
            System.out.println("Previous 2: " + previous);

            // Fetch in the ResultSet
            while (resultSet.next()) {
                // Get value of column 2
                String empNo = resultSet.getString(2);

                // Then get the value of column 1.
                int empId = resultSet.getInt(1);

                String empName = resultSet.getString("Emp_Name");

                System.out.println("--------------------");
                System.out.println("EmpId:" + empId);
                System.out.println("EmpNo:" + empNo);
                System.out.println("EmpName:" + empName);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
