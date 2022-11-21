package org.example.dao.jdbc;

import org.example.dao.ShoppingCartDAO;
import org.example.entity.Product;
import org.example.entity.ShoppingCart;
import org.example.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JdbsShoppingCartDAOTest {

    private static ShoppingCartDAO shoppingCartDAO = new JdbsShoppingCartDAO();

    @Test
    void addProductUser() {

        ShoppingCart shoppingCart = getShoppingCart();
        int expected = 0;
        int actual = shoppingCartDAO.addProductUser(shoppingCart);

        assertEquals(expected, actual);
    }

    @Test
    void removeProductUser() {
        ShoppingCart shoppingCart = getShoppingCart();

        int expected = 0;
        int actual = shoppingCartDAO.removeProductUser(shoppingCart);

        assertEquals(expected, actual);
    }

    @Test
    void getAllProductsUser() {
        int expected = 0;
        int actual = shoppingCartDAO.getAllProductsUser(0).size();

        assertEquals(expected, actual);
    }

    @Test
    void removeAllProductsUser() {
        int expected = 0;
        int actual = shoppingCartDAO.removeAllProductsUser(0);

        assertEquals(expected, actual);
    }

    private ShoppingCart getShoppingCart(){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setProductId(0);
        shoppingCart.setUserId(0);

        return shoppingCart;
    }
}