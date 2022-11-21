package org.example.dao.hibernate;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.OrderDAO;
import org.example.entity.Order;
import org.example.entity.User;
import org.example.entity.UserDetails;
import org.example.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateOrderDAO extends UtilHibernate implements OrderDAO {

    private static Logger logger = LogManager.getLogger();
    private SessionFactory sessionFactory;

    public HibernateOrderDAO(){
        addAnnotated(Order.class);
        addAnnotated(User.class);
        addAnnotated(UserDetails.class);
        sessionFactory = createSessionFactory();
    }

    @Override
    public int addOrder(Order order) {
        logger.trace("Start method HibernateOrderDao addOrder");
        int startCount = countOrders();
        Session sessionAdd = sessionFactory.openSession();
        Transaction transaction = sessionAdd.beginTransaction();
        sessionAdd.persist(order);
        transaction.commit();
        sessionAdd.close();
        logger.trace("End method HibernateOrderDao addOrder");
        return countOrders()-startCount;
    }

    @Override
    public List<Order> getOrdersUser(int userId) {
        logger.trace("Start method HibernateOrderDao getOrderUser");
        Session sessionGetOrderUser = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = sessionGetOrderUser.getCriteriaBuilder();

        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        CriteriaQuery<Order> queryGetOrdersUser = criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("userId"), userId));

        TypedQuery<Order> typedQuery = sessionGetOrderUser.createQuery(queryGetOrdersUser);
        List<Order> orderListUser = typedQuery.getResultList();

        sessionGetOrderUser.close();
        logger.trace("End method HibernateOrderDao getOrderUser");

        return orderListUser;
    }

    @Override
    public List<Order> getAllOrder() {
        logger.trace("Start method HibernateOrderDao getAllOrder");

        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        CriteriaQuery<Order> queryGetAll = criteriaQuery.select(root);
        TypedQuery<Order> typedQuery = session.createQuery(queryGetAll);

        List<Order> orderList = typedQuery.getResultList();

        session.close();
        logger.trace("End method HibernateOrderDao getAllOrder");

        return orderList;
    }

    private int countOrders(){
        Session session = sessionFactory.openSession();
        String hql = "SELECT COUNT(ord.orderId)" +
                "FROM Order ord";
        Query query = session.createQuery(hql);
        long count = (long) query.list().get(0);
        session.close();

        return (int) count;
    }

}
