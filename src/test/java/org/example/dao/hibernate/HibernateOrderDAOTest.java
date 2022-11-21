package org.example.dao.hibernate;

import org.example.dao.OrderDAO;
import org.example.entity.Order;
import org.example.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class HibernateOrderDAOTest {
    private OrderDAO orderDAO = new HibernateOrderDAO();

    @Test
    void addOrder() {
        User user = new User();
        user.setUserId(0);

        Order order = new Order();

        order.setOrderId(0);
        order.setListProduct("milk");
        order.setOrderPrice(50);
        order.setUser(user);
        order.setOrderDate(new Date(System.currentTimeMillis()));

        int expected = 0;
        int actual = orderDAO.addOrder(order);

        assertEquals(expected, actual);
    }
}