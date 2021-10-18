package com.katyshevtseva.hibernate;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public class CoreDao {
    public <T> List<T> getAll(String className) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        String sql = "From " + className;
        List<T> lists = session.createQuery(sql).list();

        session.getTransaction().commit();

        return lists;
    }

    public <T> Serializable saveNewAndGetId(T t) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Serializable id = session.save(t);

        session.getTransaction().commit();
        return id;
    }

    public <T> void saveNew(T t) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        session.save(t);

        session.getTransaction().commit();
    }

    public <T> void update(T t) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        session.update(t);

        session.getTransaction().commit();
    }

    public <T> void delete(T t) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        session.delete(t);

        session.getTransaction().commit();
    }
}
