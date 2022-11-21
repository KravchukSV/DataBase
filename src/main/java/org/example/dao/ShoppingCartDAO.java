package org.example.dao;

import org.example.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartDAO {

    int addProductUser(ShoppingCart shoppingCart);
    int removeProductUser(ShoppingCart shoppingCart);
    List<ShoppingCart> getAllProductsUser(int userId);
    int removeAllProductsUser(int userId);

}
