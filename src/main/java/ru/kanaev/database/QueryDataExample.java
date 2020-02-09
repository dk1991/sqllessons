package ru.kanaev.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryDataExample {
    private static final String SELECT_ALL = "SELECT * FROM Employee";

    public static void main(String[] args) {

        try (Connection connection = MySQLConnUtils.getMySQLConnection()) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                int empId = resultSet.getInt("Emp_id");
                String empNo = resultSet.getString("Emp_No");
                String empName = resultSet.getString("Emp_Name");
                System.out.println("--------------------");
                System.out.println("EmpId: " + empId);
                System.out.println("EmpNo: " + empNo);
                System.out.println("EmpName: " + empName);
            }

            statement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
