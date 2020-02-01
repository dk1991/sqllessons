package ru.kanaev.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionExample {
    private static final String URL = "jdbc:mysql://localhost/mydbtest?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Jumper1202";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                Statement statement = connection.createStatement();
                //System.out.println(connection.isClosed());

                //statement.execute("INSERT INTO animal(anim_name, anim_desc) VALUES ('whale','the biggest animal in the world')");

                // метод для запросов INSERT UPDATE DELETE, получать данные НЕЛЬЗЯ этим методом.
                // Возвращает кол-во записей, в которых произошло изменение.
                /*int res = statement.executeUpdate("UPDATE animal SET anim_name = 'elephant', anim_desc = 'animal with big ears'" +
                        "WHERE id = 1");
                System.out.println(res);*/

                // метод только для SELECT-ов и получения ResultSet-ов.
                ResultSet resultSet = statement.executeQuery("SELECT * FROM animal");

                // Пакетная обработка. За 1 выполнение executeBatch вставим сразу 3 записи.
                /*statement.addBatch("INSERT INTO animal(anim_name, anim_desc) VALUES ('whale','the biggest animal in the world')");
                statement.addBatch("INSERT INTO animal(anim_name, anim_desc) VALUES ('tiger','dangerous animal')");
                statement.addBatch("INSERT INTO animal(anim_name, anim_desc) VALUES ('лев','царь зверей')");
                statement.executeBatch();*/
                // statement.clearBatch(); // потереть старые запросы

                boolean status = statement.isClosed();// проверка закрытия statement-а
                System.out.println(status);

                statement.getConnection(); // получить соединение к БД

                statement.close(); // закрыть соединение (если не объявляли Statement в ресурсах блока try)
            }
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }
}
