package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl  extends Util implements UserDao {

    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        String sql = "CREATE TABLE `Users` (\n" +
                "id INT NOT NULL AUTO_INCREMENT,\n" +
                "name VARCHAR(45),\n" +
                "lastName VARCHAR(45),\n" +
                "age TINYINT NOT NULL, \n" +
                "PRIMARY KEY (id)); ";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица удалена");

        } catch (SQLException e) {
            System.out.println("Таблицы с таким именем не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO `Users`(name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("Пользователь записан");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM USERS WHERE ID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();
            System.out.println("Пользователь удален");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();

        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM USERS";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                usersList.add(user);
            }

            System.out.println("Все пользователи получены");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();

            System.out.println("Таблица очищена");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
