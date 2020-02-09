package ru.kanaev.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrepareStatementExample {
    private static final String SELECT = "SELECT * FROM employee WHERE Emp_Name like ?";

    public static void main(String[] args) {
        try (Connection connection = MySQLConnUtils.getMySQLConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);

            // Set value for the first parameter (First '?')
            preparedStatement.setString(1, "Dima");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(" ---- ");
                System.out.println("EmpId : " + resultSet.getInt("Emp_Id"));
                System.out.println("EmpNo : " + resultSet.getString(2));
                System.out.println("EmpName : " + resultSet.getString("Emp_Name"));
            }

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
