package org.example.dao.hibernate;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.dao.UserDetailsDAO;
import org.example.entity.UserDetails;
import org.example.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateUserDetailsDAO extends UtilHibernate implements UserDetailsDAO {
    private SessionFactory sessionFactory;

    public HibernateUserDetailsDAO(){
        sessionFactory = createSessionFactory(UserDetails.class);
    }

    @Override
    public int add(UserDetails userDetails) {
        Session sessionAdd = sessionFactory.openSession();
        Transaction transactionAdd = sessionAdd.beginTransaction();
        sessionAdd.persist(userDetails);
        transactionAdd.commit();
        sessionAdd.close();
        return 0;
    }

    @Override
    public List<UserDetails> getAll() {
        Session sessionGetAll = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = sessionGetAll.getCriteriaBuilder();
        CriteriaQuery<UserDetails> criteriaQuery = criteriaBuilder.createQuery(UserDetails.class);
        Root<UserDetails> rootEntry = criteriaQuery.from(UserDetails.class);

        CriteriaQuery<UserDetails> all = criteriaQuery.select(rootEntry);
        TypedQuery<UserDetails> allQuery = sessionGetAll.createQuery(all);
        //sessionGetAll.close();
        return  allQuery.getResultList();
    }

    @Override
    public UserDetails getById(int id) {
        Session sessionGetById = sessionFactory.openSession();
        UserDetails user = sessionGetById.get(UserDetails.class, id);
        sessionGetById.close();
        return user;
    }

    @Override
    public int update(UserDetails userDetails) {
        Session sessionUpdate = sessionFactory.openSession();
        Transaction transactionUpdate = sessionUpdate.beginTransaction();
        sessionUpdate.merge(userDetails);
        transactionUpdate.commit();
        sessionUpdate.close();
        return 0;
    }

    @Override
    public int remove(UserDetails userDetails) {
        Session sessionRemove = sessionFactory.openSession();
        Transaction transactionRemove = sessionRemove.beginTransaction();
        sessionRemove.remove(userDetails);
        transactionRemove.commit();
        sessionRemove.close();
        return 0;
    }
}
