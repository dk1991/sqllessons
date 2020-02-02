package ru.kanaev.database;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Calendar;

public class DBWorker {
    private static final String URL = "jdbc:mysql://localhost/mydbtest?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Jumper1202";

    private static final String INSERT_NEW = "INSERT INTO dish VALUES(?,?,?,?,?,?,?)";
    private static final String GET_ALL = "SELECT * FROM dish";
    private static final String DELET = "DELETE FROM dish WHERE id = ?";

    public static void main(String[] args) {
        try {
            // Class.forName для драйвера MySQL версии JDBC 4+ уже не нужен. Только для предыдущих версий.
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                //Statement statement = connection.createStatement();
                //System.out.println(connection.isClosed());

                //statement.execute("INSERT INTO animal(anim_name, anim_desc) VALUES ('whale','the biggest animal in the world')");

                // метод для запросов INSERT UPDATE DELETE, получать данные НЕЛЬЗЯ этим методом.
                // Возвращает кол-во записей, в которых произошло изменение.
                /*int res = statement.executeUpdate("UPDATE animal SET anim_name = 'elephant', anim_desc = 'animal with big ears'" +
                        "WHERE id = 1");
                System.out.println(res);*/

                // метод только для SELECT-ов и получения ResultSet-ов.
                //ResultSet resultSet = statement.executeQuery("SELECT * FROM animal");

                // Пакетная обработка. За 1 выполнение executeBatch вставим сразу 3 записи.
                /*statement.addBatch("INSERT INTO animal(anim_name, anim_desc) VALUES ('whale','the biggest animal in the world')");
                statement.addBatch("INSERT INTO animal(anim_name, anim_desc) VALUES ('tiger','dangerous animal')");
                statement.addBatch("INSERT INTO animal(anim_name, anim_desc) VALUES ('лев','царь зверей')");
                statement.executeBatch();*/
                // statement.clearBatch(); // потереть старые запросы

                /*boolean status = statement.isClosed();// проверка закрытия statement-а
                System.out.println(status);*/

                //statement.getConnection(); // получить соединение к БД

                /*String query = "SELECT * FROM users";
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    User user = new User();
                    *//*user.setId(resultSet.getInt(1));
                    user.setUsername(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));*//*
                    // но лучше так:
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));

                    System.out.println(user);
                }*/


                // PREPARED statement - скомпилированные запросы
                PreparedStatement preparedStatement = connection.prepareStatement(DELET);
                preparedStatement.setInt(1,2);
                int result = preparedStatement.executeUpdate(); // выполнить запрос и вернуть число записей (см ниже)
                System.out.println(result); // число записей на которые сработал запрос (в данном случае удаленных)


                preparedStatement = connection.prepareStatement(INSERT_NEW);
                // в INSERT_NEW стоят ? вместо данных, вместо них надо вставлять свои данные:
                preparedStatement.setInt(1,2);
                preparedStatement.setString(2,"Title");
                preparedStatement.setString(3,"Description");
                preparedStatement.setFloat(4, 0.5f);
                preparedStatement.setBoolean(5, false);
                preparedStatement.setDate(6, new Date(Calendar.getInstance().getTimeInMillis()));
                preparedStatement.setBlob(7, new FileInputStream("./src/main/resources/vodoley.png"));
                preparedStatement.execute(); // выполнить запрос


                preparedStatement = connection.prepareStatement(GET_ALL);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    float rating = resultSet.getFloat("rating");
                    boolean published = resultSet.getBoolean("published");
                    Date date = resultSet.getDate("created");
                    byte[] icon = resultSet.getBytes("icon");

                    System.out.println("id: " + id + " title: " + title + " description: " + description + " rating: " + rating
                    + " published: " + published + " date: " + date + " icon length: " + icon.length);
                }

                preparedStatement.close(); // закрываем наш PreparedStatement, можно делать в блоке finally или НЕ закрывать,
                // если открывали в блоке ресурсов try (PreparedStatement preparedStatement = connection.prepareStatement(DELET);) {}



                //statement.close(); // закрыть соединение (если не объявляли Statement в ресурсах блока try)
            }
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }
}
