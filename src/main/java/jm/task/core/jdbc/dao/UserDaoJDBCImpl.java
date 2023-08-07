package jm.task.core.jdbc.dao;

import java.sql.*;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final  Connection connection = Util.getConnection();
    public UserDaoJDBCImpl(UserDaoHibernateImpl userDaoHibernate) {
        // Класс dao должен иметь конструктор пустой/по умолчанию
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE task (" +
                "  `ID` INT AUTO_INCREMENT PRIMARY KEY NOT NULL," +
                "  `Name` VARCHAR(50) NULL," +
                "  `lastname` VARCHAR(50) NULL," +
                "  `Age` INT NOT NULL)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS task")){
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO task" +
                "(Name, lastName, Age) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.getCause();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM task WHERE id = ?")) {
            statement.setLong(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement()
                .executeQuery("SELECT * FROM task")){

            while (resultSet.next()) {
                User users = new User(
                        resultSet.getString("Name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("Age"));
                users.setId(resultSet.getLong("ID"));
                users.toString();
                userList.add(users);
            }
        } catch (SQLException e) {
            e.getCause();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE task");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}

