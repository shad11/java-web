package com.tinder.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tinder.exception.DataBaseException;
import com.tinder.model.Message;
import com.tinder.util.HibernateUtil;

public class MessageDaoImpl implements MessageDAO {
    @Override
    public void create(Message message) throws DataBaseException {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.save(message);
            // refresh the entity to get the timestamp from the database
            session.refresh(message);

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
    public List<Message> getAll(int senderId, int receiverId) throws DataBaseException {
        List<Message> messages = new ArrayList<>();
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM Message WHERE (senderId = :senderId AND receiverId = :receiverId) OR (senderId = :receiverId AND receiverId = :senderId) ORDER BY timestamp";
            Query<Message> query = session.createQuery(hql, Message.class);

            query.setParameter("senderId", senderId);
            query.setParameter("receiverId", receiverId);

            messages = query.list();
        } catch (Exception e) {
            e.printStackTrace();

            throw new DataBaseException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return messages;
    }
}
