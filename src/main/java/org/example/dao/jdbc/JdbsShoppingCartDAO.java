package org.example.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.bl.Util;
import org.example.dao.ShoppingCartDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbsShoppingCartDAO extends Util implements ShoppingCartDAO {
    private static Logger logger = LogManager.getLogger();
    @Override
    public int addProductUser(int productId, int userId) {
        logger.trace("Start method JdbsShoppingCartDAO addProductUser");

        int resultAdd = 0;
        String sql = "INSERT INTO shopping_cart(user_id, product_id) VALUES(?, ?)";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);

            resultAdd = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Failed to add product to cart!!!");
        }

        logger.trace("End method JdbsShoppingCartDAO addProductUser");
        return resultAdd;
    }

    @Override
    public int removeProductUser(int productId, int userId) {
        logger.trace("Start method JdbsShoppingCartDAO removeProductUser");

        int resultRemove = 0;
        String sql = "DELETE FROM shopping_cart WHERE user_id = ? AND product_id = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);

            resultRemove = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Failed to remove product to cart!!!");
        }

        logger.trace("End method JdbsShoppingCartDAO removeProductUser");
        return resultRemove;
    }

    @Override
    public List<Integer> getAllProductsUser(int userId) {
        logger.trace("Start method JdbsShoppingCartDAO getAllProductsUser");

        List<Integer> listProductId = new ArrayList<>();
        String sql = "SELECT product_id FROM shopping_cart WHERE user_id = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = preparedStatement(connection, sql, userId);
                ResultSet resultSet = preparedStatement.executeQuery()

        ){
            while (resultSet.next()){
                listProductId.add(resultSet.getInt("product_id"));
            }

        } catch (SQLException e) {
            logger.error("Failed to get all products user to cart!!!");
        }

        logger.trace("End method JdbsShoppingCartDAO getAllProductUser");
        return listProductId;
    }

    @Override
    public int removeAllProductsUser(int userId) {
        logger.trace("Start method JdbsShoppingCartDAO removeAllProductsUser");

        int resultRemove = 0;
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, userId);

            resultRemove = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Failed to remove all products to cart!!!");
        }

        logger.trace("End method JdbsShoppingCartDAO removeAllProductsUser");
        return resultRemove;
    }

    private PreparedStatement preparedStatement(Connection connection, String sql, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);

        return preparedStatement;
    }
}
