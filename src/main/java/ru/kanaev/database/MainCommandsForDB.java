package ru.kanaev.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class MainCommandsForDB {
    /*private static final String URL = "jdbc:mysql://localhost/store?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Jumper1202";*/

    private static final String CREATE_TABLE = "CREATE TABLE products (Id INT PRIMARY KEY AUTO_INCREMENT," +
            "ProductName VARCHAR(20), Price INT)";

    public static void main(String[] args) {
        try (Connection connection = getConnection()/*DriverManager.getConnection(URL,USER_NAME, PASSWORD)*/) {
            Statement statement = connection.createStatement();
            // Создание таблицы
            //statement.executeUpdate(CREATE_TABLE);

            // Добавление элементов в таблицу
            /*int rows = statement.executeUpdate("INSERT INTO products (ProductName, Price) VALUES ('iPad', 25000)," +
                    "('Galaxy', 20000), ('Lenovo', 16000)");
            System.out.printf("Added %d rows", rows);*/

            // Изменение данных в таблице
            /*int rows = statement.executeUpdate("UPDATE products SET Price = Price - 2500");
            System.out.printf("Updated %d rows", rows);*/

            // Удалить данные из таблицы
            /*int rows = statement.executeUpdate("DELETE FROM products WHERE Id = 2");
            System.out.printf("Deleted %d rows", rows);*/

            // Получение данных из таблицы
            /*ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String productName = resultSet.getString("ProductName");
                int price = resultSet.getInt("Price");

                System.out.printf("%d. %s - %d rub\n", id, productName, price);
            }*/

            /*Scanner scanner = new Scanner(System.in);
            System.out.print("Input product name: ");
            String name = scanner.nextLine();
            System.out.print("Input product price: ");
            int price = scanner.nextInt();
            // PreparedStatement - Скомпилированный запрос
            String sql = "INSERT INTO products (ProductName, Price) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            int rows = preparedStatement.executeUpdate();
            System.out.printf("%d rows added", rows);*/

            int maxPrice = 20000;
            String sql1 = "SELECT * FROM products WHERE Price < ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1,maxPrice);
            ResultSet resultSet = preparedStatement1.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("Id");
                String name1 = resultSet.getString("ProductName");
                int price1 = resultSet.getInt("Price");

                System.out.printf("%d. %s - %d \n", id, name1, price1);
            }

            // т.к. не в блоке ресурсов try, можно закрывать в блоке finally
            preparedStatement1.close();
            statement.close();
        } catch (SQLException | IOException e) {
            System.out.println("Connection failed...");
            System.out.println(e);
        }
    }

    public static Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(Paths.get("./src/main/resources/database.properties"))) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        return DriverManager.getConnection(url,username,password);
    }
}
