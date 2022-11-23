package org.example.dao.hibernate;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.ProductDAO;
import org.example.entity.Product;
import org.example.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateProductDAO extends UtilHibernate implements ProductDAO {
    private SessionFactory sessionFactory;
    private static Logger logger = LogManager.getLogger();

    public HibernateProductDAO(){
        addAnnotated(Product.class);
        sessionFactory = createSessionFactory();
    }

    @Override
    public int add(Product product) {
        logger.trace("Start method HibernateProductDao add");
        int startCount = countProducts();
        Session sessionAdd = sessionFactory.openSession();
        Transaction transactionAdd = sessionAdd.beginTransaction();
        try{
            sessionAdd.persist(product);
            transactionAdd.commit();
        }
        catch (PersistenceException persistenceException){
            transactionAdd.rollback();
            logger.error("No new product added!!!");
        }
        sessionAdd.close();
        logger.trace("End method HibernateProductDao add");

        return countProducts() - startCount;
    }

    @Override
    public List<Product> getAll() {
        logger.trace("Start method HibernateProductDao getAll");
        Session sessionGetAll = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = sessionGetAll.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> rootEntry = criteriaQuery.from(Product.class);

        CriteriaQuery<Product> all = criteriaQuery.select(rootEntry);
        TypedQuery<Product> allQuery = sessionGetAll.createQuery(all);
        List<Product> productList = allQuery.getResultList();

        sessionGetAll.close();
        logger.trace("End method HibernateProductDao getAll");

        return  productList;
    }

    @Override
    public Product getById(int id) {
        logger.trace("Start method HibernateProductDao getById");

        Session sessionGetById = sessionFactory.openSession();
        Product product = sessionGetById.get(Product.class, id);
        sessionGetById.close();

        logger.trace("End method HibernateProductDao getById");
        return product;
    }

    @Override
    public int update(Product product) {
        logger.trace("Start method HibernateProductDao update");
        int startCount = countProducts();
        Session sessionUpdate = sessionFactory.openSession();
        Transaction transactionUpdate = sessionUpdate.beginTransaction();

        Product productToChanges = getById(product.getProductId());

        sessionUpdate.merge(product);
        transactionUpdate.commit();
        sessionUpdate.close();

        logger.trace("End method HibernateProductDao update");
        return productToChanges.equals(product)?0:1;
    }

    @Override
    public int remove(Product product) {
        logger.trace("Start method HibernateProductDao remove");
        int startCount = countProducts();

        Session sessionRemove = sessionFactory.openSession();
        Transaction transactionRemove = sessionRemove.beginTransaction();
        sessionRemove.remove(product);
        transactionRemove.commit();
        sessionRemove.close();

        logger.trace("End method HibernateProductDao remove");

        return countProducts() - startCount;
    }

    private int countProducts(){
        Session session = sessionFactory.openSession();
        String hql = "SELECT COUNT(product.id)" +
                "FROM Product product";
        Query query = session.createQuery(hql);
        Long count;
        try {
            count = (Long) query.list().get(0);
        }
        catch (ClassCastException e){
            return 0;
        }
        finally {
            session.close();
        }

        return Math.toIntExact(count);
    }
}
