package org.example.dao;

import org.example.entity.Order;

import java.util.List;

public interface OrderDAO {
    int addOrder(Order order);
    List<Order> getOrdersUser(int userId);
    List<Order> getAllOrder();
}
