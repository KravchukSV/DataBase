package org.example.dao.hibernate;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.ShoppingCartDAO;
import org.example.entity.ShoppingCart;
import org.example.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateShoppingCartDAO extends UtilHibernate implements ShoppingCartDAO {
    private SessionFactory sessionFactory;
    private static Logger logger = LogManager.getLogger();


    public HibernateShoppingCartDAO(){
        addAnnotated(ShoppingCart.class);
        sessionFactory = createSessionFactory();
    }

    @Override
    public int addProductUser(ShoppingCart shoppingCart) {
        logger.trace("Start method HibernateShoppingCartDao addProductUser");
        int startCount = countShoppingCarts();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.persist(shoppingCart);
            transaction.commit();
        }
        catch (PersistenceException persistenceException){
            transaction.rollback();
            logger.error("Failed to add product to cart!!!");
        }

        session.close();

        logger.trace("End method HibernateShoppingCartDao addProductUser");

        return countShoppingCarts() - startCount;
    }

    @Override
    public int removeProductUser(ShoppingCart shoppingCart) {
        logger.trace("Start method HibernateShoppingCartDao removeProductUser");
        int startCount = countShoppingCarts();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.remove(shoppingCart);

        transaction.commit();
        session.close();

        logger.trace("End method HibernateShoppingCartDao removeProductUser");
        return countShoppingCarts() - startCount;
    }

    @Override
    public List<ShoppingCart> getAllProductsUser(int userId) {
        logger.trace("Start method HibernateShoppingCartDao getAllProductUser");

        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ShoppingCart> criteriaQuery = criteriaBuilder.createQuery(ShoppingCart.class);
        Root<ShoppingCart> root = criteriaQuery.from(ShoppingCart.class);

        CriteriaQuery<ShoppingCart> queryAllProductsUser = criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("userId"), userId));

        TypedQuery<ShoppingCart> typedQuery = session.createQuery(queryAllProductsUser);
        List<ShoppingCart> list = typedQuery.getResultList();

        session.close();
        logger.trace("End method HibernateShoppingCartDao getAllProductUser");

        return list;
    }

    @Override
    public int removeAllProductsUser(int userId) {
        logger.trace("Start method HibernateShoppingCartDao removeAllProductUser");
        int startCount = countShoppingCarts();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<ShoppingCart> criteriaDelete = criteriaBuilder.createCriteriaDelete(ShoppingCart.class);
        Root<ShoppingCart> root = criteriaDelete.from(ShoppingCart.class);
        CriteriaDelete<ShoppingCart> deleteAllProductUser = criteriaDelete
                .where(criteriaBuilder.equal(root.get("userId"), userId));

        session.createQuery(deleteAllProductUser).executeUpdate();
        transaction.commit();
        session.close();

        logger.trace("End method HibernateShoppingCartDao removeAllProductUser");

        return countShoppingCarts() - startCount;
    }

    private int countShoppingCarts(){
        Session session = sessionFactory.openSession();
        String hql = "SELECT COUNT(sc.id)" +
                "FROM ShoppingCart sc";
        Query query = session.createQuery(hql);
        long count = (long) query.list().get(0);
        session.close();

        return (int) count;
    }
}
