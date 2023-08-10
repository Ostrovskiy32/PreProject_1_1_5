package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.util.Properties;
import java.sql.*;


public class Util {
      private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/task1/1/4";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "2810";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection is successful");
        } catch (ClassNotFoundException | SQLException e) {
            e.getStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }
    public static SessionFactory getSessionFactory() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.connection.driver_class", DB_DRIVER);
        properties.setProperty("hibernate.connection.url", DB_URL);
        properties.setProperty("hibernate.connection.username", DB_USERNAME);
        properties.setProperty("hibernate.connection.password", DB_PASSWORD);
        properties.setProperty("show_sql", String.valueOf(true));
        properties.setProperty("dialect", "(org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto","create-drop");


        return new Configuration().addAnnotatedClass(User.class).addProperties(properties).buildSessionFactory();
    }
}
