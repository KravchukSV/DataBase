package org.example;

import org.example.dao.UserDAO;
import org.example.dao.hibernate.HibernateFactoryDAO;
import org.example.dao.jdbc.JdbcFactoryDAO;
import org.example.dao.jdbc.JdbsUserDAO;
import org.example.entity.User;
import org.example.service.OrderService;



public class Main {
    public static void main(String[] args) {
        testHibernate();
    }

    public static void testJDBC(){
        UserDAO userDAO = new JdbsUserDAO();
        User user = userDAO.getById(9);

        OrderService orderService = new OrderService(new JdbcFactoryDAO());
        orderService.toOrder(user);
    }

    public static void testHibernate(){
        HibernateFactoryDAO hibernateFactoryDAO = new HibernateFactoryDAO();

        User user = hibernateFactoryDAO.getUserDAO().getById(9);

        OrderService orderService = new OrderService(hibernateFactoryDAO);
        orderService.toOrder(user);
    }
}