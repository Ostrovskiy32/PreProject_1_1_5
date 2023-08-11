package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory factory = Util.getSessionFactory();
    User user = new User();
    public UserDaoHibernateImpl() {
        // Класс dao должен иметь конструктор пустой/по умолчанию
    }


    @Override
    public void createUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE task (" +
                    "  `ID` INT AUTO_INCREMENT PRIMARY KEY NOT NULL," +
                    "  `Name` VARCHAR(50) NULL," +
                    "  `lastName` VARCHAR(50) NULL," +
                    "  `Age` INT NOT NULL)";
            session.createSQLQuery(sql);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getCause();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS task");//конфигурация hibernate.hbm2ddl.auto позволяет в автоматическом режиме обновлять DB.
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getCause();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        user = new User(name, lastName, age);
        Session session = factory.openSession();
        try (session) {
            session.beginTransaction();
            session.save (user);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction().isActive()){
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getCause();
        }
    }

    @Override
    public List<User> getAllUsers() {

        try (Session session = factory.openSession()) {
            return session.createQuery("from User").getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getCause();
        }
    }
}

