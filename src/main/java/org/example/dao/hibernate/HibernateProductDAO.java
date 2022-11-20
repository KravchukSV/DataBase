package org.example.dao.hibernate;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.dao.ProductDAO;
import org.example.entity.Product;
import org.example.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateProductDAO extends UtilHibernate implements ProductDAO {
    private SessionFactory sessionFactory;

    public HibernateProductDAO(){
        sessionFactory = createSessionFactory(Product.class);
    }

    @Override
    public int add(Product product) {
        Session sessionAdd = sessionFactory.openSession();
        Transaction transactionAdd = sessionAdd.beginTransaction();
        sessionAdd.persist(product);
        transactionAdd.commit();
        sessionAdd.close();
        return 0;
    }

    @Override
    public List<Product> getAll() {
        Session sessionGetAll = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = sessionGetAll.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> rootEntry = criteriaQuery.from(Product.class);

        CriteriaQuery<Product> all = criteriaQuery.select(rootEntry);
        TypedQuery<Product> allQuery = sessionGetAll.createQuery(all);
        //sessionGetAll.close();
        return  allQuery.getResultList();
    }

    @Override
    public Product getById(int id) {
        Session sessionGetById = sessionFactory.openSession();
        Product product = sessionGetById.get(Product.class, id);
        sessionGetById.close();
        return product;
    }

    @Override
    public int update(Product product) {
        Session sessionUpdate = sessionFactory.openSession();
        Transaction transactionUpdate = sessionUpdate.beginTransaction();
        sessionUpdate.merge(product);
        transactionUpdate.commit();
        sessionUpdate.close();
        return 0;
    }

    @Override
    public int remove(Product product) {
        Session sessionRemove = sessionFactory.openSession();
        Transaction transactionRemove = sessionRemove.beginTransaction();
        sessionRemove.remove(product);
        transactionRemove.commit();
        sessionRemove.close();
        return 0;
    }
}
