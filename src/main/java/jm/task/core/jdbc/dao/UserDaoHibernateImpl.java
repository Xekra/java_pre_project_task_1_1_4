package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String sql = "CREATE TABLE IF NOT EXISTS USERS \n" +
                        "(\n" +
                        "ID INT NOT NULL AUTO_INCREMENT," +
                        "NAME VARCHAR(50) NOT NULL," +
                        "LAST_NAME VARCHAR(50) NOT NULL," +
                        "AGE INTEGER NOT NULL, PRIMARY KEY (ID)\n" +
                        ");";
                session.createSQLQuery(sql).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String sql = "DROP TABLE IF EXISTS USERS";
                session.createSQLQuery(sql).executeUpdate();
                transaction.commit();
            } catch(Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                User user = new User(name, lastName,age);
                session.save(user);
                transaction.commit();
                System.out.println("User с именем - " + user.getName() + " добавлен в базу данных");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createQuery("DELETE FROM User WHERE id = :idUser")
                        .setParameter("idUser", id).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String sql = "FROM User";
                userList = session.createQuery(sql).list();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null){
                    transaction.rollback();
                    e.printStackTrace();
                }
            }
            return userList;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String sql = "DELETE FROM User";
                session.createQuery(sql).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                    e.printStackTrace();
                }
            }
        }
    }
}
