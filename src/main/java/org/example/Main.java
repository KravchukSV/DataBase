package org.example;

import org.example.dao.UserDAO;
import org.example.dao.jdbc.JdbcFactoryDAO;
import org.example.dao.jdbc.JdbsUserDAO;
import org.example.entity.User;
import org.example.service.OrderService;

public class Main {
    public static void main(String[] args) {

        UserDAO userDAO = new JdbsUserDAO();
        User user = userDAO.getById(9);

        OrderService orderService = new OrderService(new JdbcFactoryDAO());
        orderService.toOrder(user);
    }
}