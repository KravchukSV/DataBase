package org.example.dao.hibernate;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.UserDetailsDAO;
import org.example.entity.UserDetails;
import org.example.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateUserDetailsDAO extends UtilHibernate implements UserDetailsDAO {
    private SessionFactory sessionFactory;
    private static Logger logger = LogManager.getLogger();

    public HibernateUserDetailsDAO(){
        addAnnotated(UserDetails.class);
        sessionFactory = createSessionFactory();
    }

    @Override
    public int add(UserDetails userDetails) {
        logger.trace("Start method HibernateUserDetailsDao add");
        int startCount = countUserDetails();

        Session sessionAdd = sessionFactory.openSession();
        Transaction transactionAdd = sessionAdd.beginTransaction();
        sessionAdd.persist(userDetails);
        transactionAdd.commit();
        sessionAdd.close();

        logger.trace("End method HibernateUserDetailsDao add");

        return countUserDetails() - startCount;
    }

    @Override
    public List<UserDetails> getAll() {
        logger.trace("Start method HibernateUserDetailsDao getAll");

        Session sessionGetAll = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = sessionGetAll.getCriteriaBuilder();
        CriteriaQuery<UserDetails> criteriaQuery = criteriaBuilder.createQuery(UserDetails.class);
        Root<UserDetails> rootEntry = criteriaQuery.from(UserDetails.class);

        CriteriaQuery<UserDetails> all = criteriaQuery.select(rootEntry);
        TypedQuery<UserDetails> allQuery = sessionGetAll.createQuery(all);

        List<UserDetails> userDetailsList = allQuery.getResultList();
        sessionGetAll.close();

        logger.trace("End method HibernateUserDetailsDao getAll");

        return  userDetailsList;
    }

    @Override
    public UserDetails getById(int id) {
        logger.trace("Start method HibernateUserDetailsDao getById");

        Session sessionGetById = sessionFactory.openSession();
        UserDetails user = sessionGetById.get(UserDetails.class, id);
        sessionGetById.close();

        logger.trace("End method HibernateUserDetailsDao getById");
        return user;
    }

    @Override
    public int update(UserDetails userDetails) {
        logger.trace("Start method HibernateUserDetailsDao update");
        int startCount = countUserDetails();

        Session sessionUpdate = sessionFactory.openSession();
        Transaction transactionUpdate = sessionUpdate.beginTransaction();
        sessionUpdate.merge(userDetails);
        transactionUpdate.commit();
        sessionUpdate.close();

        logger.trace("End method HibernateUserDetailsDao update");

        return countUserDetails() - startCount;
    }

    @Override
    public int remove(UserDetails userDetails) {
        logger.trace("Start method HibernateUserDetailsDao remove");
        int startCount = countUserDetails();

        Session sessionRemove = sessionFactory.openSession();
        Transaction transactionRemove = sessionRemove.beginTransaction();
        sessionRemove.remove(userDetails);
        transactionRemove.commit();
        sessionRemove.close();

        logger.trace("End method HibernateUserDetailsDao remove");

        return countUserDetails() - startCount;
    }

    private int countUserDetails(){
        Session session = sessionFactory.openSession();
        String hql = "SELECT COUNT(ud.id)" +
                "FROM UserDetails ud";
        Query query = session.createQuery(hql);
        int count = (int) query.list().get(0);
        session.close();

        return count;
    }
}
