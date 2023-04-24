package com.katyshevtseva.hibernate;

import com.katyshevtseva.general.Page;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CoreDao {

    public <T> void save(boolean newOne, T t) {
        if (newOne)
            saveNew(t);
        else
            update(t);
    }

    public <T> List<T> findBy(Class<T> tClass, String propertyName, Object propertyValue) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(tClass).add(Restrictions.eq(propertyName, propertyValue));
        List<T> logs = criteria.list();

        session.getTransaction().commit();

        return logs;
    }

    public <T> Page<T> find(PageableQuery<T> query) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(query.getTClass());

        for (Map.Entry<String, Object> entry : query.getEqualityRestrictions().entrySet())
            criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));

        if (query.getOrder() != null)
            criteria.addOrder(query.getOrder());

        criteria.setFirstResult(query.getPageNum() * query.getPageSize());
        criteria.setMaxResults(query.getPageSize());

        List<T> list = criteria.list();

        Criteria criteriaCount = session.createCriteria(query.getTClass());
        for (Map.Entry<String, Object> entry : query.getEqualityRestrictions().entrySet())
            criteriaCount.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        criteriaCount.setProjection(Projections.rowCount());
        Long count = (Long) criteriaCount.uniqueResult();

        session.getTransaction().commit();

        return new Page<>(list, query.getPageNum(), (int) (Math.ceil(count * 1.0 / query.getPageSize())));
    }

    public <T> List<T> find(Query<T> query) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(query.getTClass());

        for (Map.Entry<String, Object> entry : query.getEqualityRestrictions().entrySet())
            criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));

        if (query.getOrder() != null)
            criteria.addOrder(query.getOrder());

        List<T> list = criteria.list();
        session.getTransaction().commit();

        return list;
    }

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
