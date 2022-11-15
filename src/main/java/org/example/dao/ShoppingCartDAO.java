package org.example.dao;

import java.util.List;

public interface ShoppingCartDAO {

    int addProductUser(int productId, int userId);
    int removeProductUser(int productId, int userId);
    List<Integer> getAllProductsUser(int userId);
    int removeAllProductsUser(int userId);

}
