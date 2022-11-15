package org.example.dao.jdbc;

import org.example.dao.OrderDAO;
import org.example.entity.Order;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class JdbsOrderDAOTest {

    private OrderDAO orderDAO = new JdbsOrderDAO();
    @Test
    void addOrder() {
        Order order = new Order();

        order.setOrderId(0);
        order.setListProduct("milk");
        order.setOrderPrice(50);
        order.setUserId(0);
        order.setOrderDate(new Date(System.currentTimeMillis()));

        int expected = 0;
        int actual = orderDAO.addOrder(order);

        assertEquals(expected, actual);
    }

    @Test
    void getOrdersUser() {
        int expected = 0;
        int actual = orderDAO.getOrdersUser(0).size();

        assertEquals(expected, actual);
    }
}