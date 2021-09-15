package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE `users` (\n" +
                "id INT NOT NULL AUTO_INCREMENT,\n" +
                "name VARCHAR(45),\n" +
                "lastName VARCHAR(45),\n" +
                "age TINYINT NOT NULL, \n" +
                "PRIMARY KEY (id)); ";
        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Taблица создана.");
        }
    }


    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE users";

        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена.");
        } catch (PersistenceException e) {
            System.out.println("Таблицы с таким именем не существует.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);

        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);

            transaction.commit();
            System.out.println("Пользователь " + name + " записан.");
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM USERS WHERE ID=".concat(String.valueOf(id));

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();

            transaction.commit();
            System.out.println("Пользователь удален.");
        }


    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList;

        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM USERS";
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            transaction.commit();

            usersList = query.list();
            for (User user : usersList) {
                System.out.println(user.getName()+" "+user.getLastName()+" "+user.getAge());
            }
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();

            System.out.println("Таблица очищена");
        }

    }
}
