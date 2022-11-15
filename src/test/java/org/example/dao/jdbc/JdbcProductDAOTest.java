package org.example.dao.jdbc;

import org.example.dao.ProductDAO;
import org.example.entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JdbcProductDAOTest {

    private ProductDAO  productDAO = new JdbcProductDAO();

    @Test
    void add() {
        Product product = new Product();

        product.setProductId(1);
        product.setName("Cucumber");
        product.setWeight(1);
        product.setPrice(60);

        int actual = productDAO.add(product);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void getById() {
        Product expectedProduct = new Product();

        expectedProduct.setProductId(1);
        expectedProduct.setName("milk");
        expectedProduct.setWeight(0.9);
        expectedProduct.setPrice(50);

        Product actualProduct = productDAO.getById(1);

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void update() {
        Product product = new Product();
        product.setProductId(1);
        product.setName("milk");
        product.setWeight(0.9);
        product.setPrice(50);

        int expected = 1;
        int actual = productDAO.update(product);

        assertEquals(expected, actual);
    }

    @Test
    void remove() {
        Product product = new Product();

        product.setProductId(0);
        product.setName("Cucumber");
        product.setWeight(1);
        product.setPrice(60);

        int expected = 0;
        int actual = productDAO.remove(product);
        assertEquals(expected, actual);
    }
}