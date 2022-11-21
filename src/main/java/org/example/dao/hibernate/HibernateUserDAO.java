package org.example.dao.hibernate;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.UserDAO;
import org.example.entity.User;
import org.example.entity.UserDetails;
import org.example.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateUserDAO extends UtilHibernate implements UserDAO {

    private SessionFactory sessionFactory;
    private static Logger logger = LogManager.getLogger();


    public HibernateUserDAO(){
        addAnnotated(User.class);
        addAnnotated(UserDetails.class);
        sessionFactory = createSessionFactory();
    }

    @Override
    public int add(User user) {
        logger.trace("Start method HibernateUserDao add");
        int startCount = countUsers();

        Session sessionAdd = sessionFactory.openSession();
        Transaction transactionAdd = sessionAdd.beginTransaction();
        try {
            sessionAdd.persist(user);
            transactionAdd.commit();
        }
        catch (PersistenceException persistenceException){
            transactionAdd.rollback();
            logger.error("No new user added!!!");
        }
        sessionAdd.close();

        logger.trace("End method HibernateUserDao add");
        return countUsers() - startCount;
    }

    @Override
    public List<User> getAll() {
        logger.trace("Start method HibernateUserDao getAll");

        Session sessionGetAll = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = sessionGetAll.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootEntry = criteriaQuery.from(User.class);

        CriteriaQuery<User> all = criteriaQuery.select(rootEntry);
        TypedQuery<User> allQuery = sessionGetAll.createQuery(all);
        List<User> resultList = allQuery.getResultList();
        sessionGetAll.close();

        logger.trace("End method HibernateUserDao getAll");

        return  resultList;
    }

    @Override
    public User getById(int id) {
        logger.trace("Start method HibernateUserDao getById");

        Session sessionGetById = sessionFactory.openSession();
        User user = sessionGetById.get(User.class, id);
        sessionGetById.close();

        logger.trace("End method HibernateUserDao getById");

        return user;
    }

    @Override
    public int update(User user) {
        logger.trace("Start method HibernateUserDao update");
        int startCount = countUsers();

        Session sessionUpdate = sessionFactory.openSession();
        Transaction transactionUpdate = sessionUpdate.beginTransaction();
        sessionUpdate.merge(user);
        transactionUpdate.commit();
        sessionUpdate.close();

        logger.trace("End method HibernateUserDao update");

        return countUsers() - startCount;
    }

    @Override
    public int remove(User user) {
        logger.trace("Start method HibernateUserDao remove");
        int startCount = countUsers();

        Session sessionRemove = sessionFactory.openSession();
        Transaction transactionRemove = sessionRemove.beginTransaction();
        sessionRemove.remove(user);
        transactionRemove.commit();
        sessionRemove.close();

        logger.trace("End method HibernateUserDao remove");

        return countUsers() - startCount;
    }

    private int countUsers(){
        Session session = sessionFactory.openSession();
        String hql = "SELECT COUNT(user.id)" +
                "FROM User user";
        Query query = session.createQuery(hql);
        int count;
        try {
            count = (int) query.list().get(0);
        }
        catch (ClassCastException e){
            return 0;
        }
        finally {
            session.close();
        }

        return count;
    }
}
