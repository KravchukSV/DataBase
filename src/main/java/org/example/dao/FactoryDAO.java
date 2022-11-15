package org.example.dao;

import org.example.entity.ShoppingCart;

public interface FactoryDAO {

    ProductDAO getProductDAO();
    UserDAO getUserDAO();
    UserDetailsDAO getUserDetailsDAO();

    ShoppingCartDAO getShoppingCartDAO();

    OrderDAO getOrderDAO();
}
