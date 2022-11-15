package org.example.dao.jdbc;

import org.example.dao.ShoppingCartDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JdbsShoppingCartDAOTest {

    private static ShoppingCartDAO shoppingCartDAO = new JdbsShoppingCartDAO();

    @Test
    void addProductUser() {

        int expected = 0;
        int actual = shoppingCartDAO.addProductUser(0, 0);

        assertEquals(expected, actual);
    }

    @Test
    void removeProductUser() {
        int expected = 0;
        int actual = shoppingCartDAO.removeProductUser(0, 0);

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
}