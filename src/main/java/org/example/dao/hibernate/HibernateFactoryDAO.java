package org.example.dao.hibernate;

import org.example.dao.*;

public class HibernateFactoryDAO implements FactoryDAO {
    private HibernateShoppingCartDAO hibernateShoppingCartDAO;
    private HibernateUserDAO hibernateUserDAO;
    private HibernateOrderDAO hibernateOrderDAO;
    private HibernateProductDAO hibernateProductDAO;
    private HibernateUserDetailsDAO hibernateUserDetailsDAO;

    public HibernateFactoryDAO(){
        hibernateShoppingCartDAO = new HibernateShoppingCartDAO();
        hibernateUserDAO = new HibernateUserDAO();
        hibernateOrderDAO = new HibernateOrderDAO();
        hibernateProductDAO = new HibernateProductDAO();
        hibernateUserDetailsDAO = new HibernateUserDetailsDAO();
    }
    @Override
    public ProductDAO getProductDAO() {
        return hibernateProductDAO;
    }

    @Override
    public UserDAO getUserDAO() {
        return hibernateUserDAO;
    }

    @Override
    public UserDetailsDAO getUserDetailsDAO() {
        return hibernateUserDetailsDAO;
    }

    @Override
    public ShoppingCartDAO getShoppingCartDAO() {
        return hibernateShoppingCartDAO;
    }

    @Override
    public OrderDAO getOrderDAO() {
        return hibernateOrderDAO;
    }
}
