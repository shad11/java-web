package com.tinder.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tinder.exception.DataBaseException;
import com.tinder.model.User;
import com.tinder.util.HibernateUtil;

public class UserDaoImpl implements UserDAO {
    @Override
    public void create(User user) throws DataBaseException {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();

            throw new DataBaseException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(String email) throws DataBaseException {
        Session session = null;
        User user = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM User WHERE email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);

            user = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();

            throw new DataBaseException(e.getMessage());
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public User get(int id) throws DataBaseException {
        Session session = null;
        User user = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();

            throw new DataBaseException(e.getMessage());
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public void update(User user) throws DataBaseException {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();

            throw new DataBaseException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Session session = null;
        Transaction transaction = null;
        User user = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            user = session.get(User.class, id);

            if (user != null) {
                session.delete(user);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();

            throw new DataBaseException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() throws DataBaseException {
        Session session = null;
        List<User> users = new ArrayList<>();

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM User";
            Query<User> query = session.createQuery(hql, User.class);

            users = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            
            throw new DataBaseException(e.getMessage());
        } finally {
            session.close();
        }

        return users;
    }
}
