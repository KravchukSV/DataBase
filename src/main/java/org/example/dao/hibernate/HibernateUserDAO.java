package org.example.dao.hibernate;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.dao.UserDAO;
import org.example.entity.User;
import org.example.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateUserDAO extends UtilHibernate implements UserDAO {

    private SessionFactory sessionFactory;

    public HibernateUserDAO(){
        sessionFactory = createSessionFactory(User.class);
    }

    @Override
    public int add(User user) {
        Session sessionAdd = sessionFactory.openSession();
        Transaction transactionAdd = sessionAdd.beginTransaction();
        sessionAdd.persist(user);
        transactionAdd.commit();
        sessionAdd.close();
        return 0;
    }

    @Override
    public List<User> getAll() {
        Session sessionGetAll = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = sessionGetAll.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootEntry = criteriaQuery.from(User.class);

        CriteriaQuery<User> all = criteriaQuery.select(rootEntry);
        TypedQuery<User> allQuery = sessionGetAll.createQuery(all);
        List<User> resultList = allQuery.getResultList();
        sessionGetAll.close();
        return  resultList;
    }

    @Override
    public User getById(int id) {
        Session sessionGetById = sessionFactory.openSession();
        User user = sessionGetById.get(User.class, id);
        sessionGetById.close();
        return user;
    }

    @Override
    public int update(User user) {
        Session sessionUpdate = sessionFactory.openSession();
        Transaction transactionUpdate = sessionUpdate.beginTransaction();
        sessionUpdate.merge(user);
        transactionUpdate.commit();
        sessionUpdate.close();
        return 0;
    }

    @Override
    public int remove(User user) {
        Session sessionRemove = sessionFactory.openSession();
        Transaction transactionRemove = sessionRemove.beginTransaction();
        sessionRemove.remove(user);
        transactionRemove.commit();
        sessionRemove.close();
        return 0;
    }
}
