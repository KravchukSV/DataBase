package org.example;

import org.example.dao.hibernate.HibernateFactoryDAO;
import org.example.dao.jdbc.JdbcFactoryDAO;
import org.example.entity.ShoppingCart;
import org.example.entity.User;
import org.example.service.OrderService;



public class Main {
    public static void main(String[] args) {
        testHibernate();
    }

    public static void testJDBC(){
        JdbcFactoryDAO jdbcFactoryDAO = new JdbcFactoryDAO();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(9);
        shoppingCart.setProductId(9);

        jdbcFactoryDAO.getShoppingCartDAO().addProductUser(shoppingCart);

        User user = jdbcFactoryDAO.getUserDAO().getById(9);

        OrderService orderService = new OrderService(new JdbcFactoryDAO());
        orderService.toOrder(user);
    }

    public static void testHibernate(){
        HibernateFactoryDAO hibernateFactoryDAO = new HibernateFactoryDAO();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(9);
        shoppingCart.setProductId(9);

        hibernateFactoryDAO.getShoppingCartDAO().addProductUser(shoppingCart);

        User user = hibernateFactoryDAO.getUserDAO().getById(9);

        OrderService orderService = new OrderService(hibernateFactoryDAO);
        orderService.toOrder(user);
    }
}