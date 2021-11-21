package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Roman", "Chulyukin", (byte) 28);
        userService.saveUser("Natalia", "Nedyuzhina", (byte) 26);
        userService.saveUser("Mia", "Cat", (byte) 2);
        userService.saveUser("Kuzya", "Cat", (byte) 4);

        for (User users : userService.getAllUsers()) {
            System.out.println(users);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
