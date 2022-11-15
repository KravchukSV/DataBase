package org.example.dao.jdbc;

import org.example.dao.*;

public class JdbcFactoryDAO implements FactoryDAO {

    private JdbcProductDAO jdbcProductDAO;
    private  JdbsUserDAO jdbsUserDAO;
    private JdbcUserDetailsDAO jdbcUserDetailsDAO;
    private JdbsOrderDAO jdbsOrderDAO;
    private JdbsShoppingCartDAO jdbsShoppingCartDAO;

    public JdbcFactoryDAO(){
        jdbcProductDAO = new JdbcProductDAO();
        jdbsUserDAO = new JdbsUserDAO();
        jdbcUserDetailsDAO = new JdbcUserDetailsDAO();
        jdbsOrderDAO = new JdbsOrderDAO();
        jdbsShoppingCartDAO = new JdbsShoppingCartDAO();
    }


    @Override
    public ProductDAO getProductDAO() {
        return jdbcProductDAO;
    }

    @Override
    public UserDAO getUserDAO() {
        return jdbsUserDAO;
    }

    @Override
    public UserDetailsDAO getUserDetailsDAO() {
        return jdbcUserDetailsDAO;
    }

    @Override
    public ShoppingCartDAO getShoppingCartDAO() {
        return jdbsShoppingCartDAO;
    }

    @Override
    public OrderDAO getOrderDAO() {
        return jdbsOrderDAO;
    }
}
