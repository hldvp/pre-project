package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Util util = new Util();
        util.getConnection();
        UserServiceImpl usi = new UserServiceImpl();
        usi.dropUsersTable();
        usi.createUsersTable();
        usi.saveUser("Ilya", "Spitsyn", (byte) 27);
    }
}
