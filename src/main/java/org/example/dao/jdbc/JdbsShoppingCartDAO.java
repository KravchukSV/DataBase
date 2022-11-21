package org.example.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.ShoppingCart;
import org.example.util.UtilJDBC;
import org.example.dao.ShoppingCartDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbsShoppingCartDAO extends UtilJDBC implements ShoppingCartDAO {
    private static Logger logger = LogManager.getLogger();
    private JdbsUserDAO jdbsUserDAO = new JdbsUserDAO();
    private JdbcProductDAO jdbcProductDAO = new JdbcProductDAO();
    @Override
    public int addProductUser(ShoppingCart shoppingCart) {
        logger.trace("Start method JdbsShoppingCartDAO addProductUser");

        int resultAdd = 0;
        String sql = "INSERT INTO shopping_cart(user_id, product_id) VALUES(?, ?)";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setInt(1, shoppingCart.getUserId());
            preparedStatement.setInt(2, shoppingCart.getProductId());

            resultAdd = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Failed to add product to cart!!!");
        }

        logger.trace("End method JdbsShoppingCartDAO addProductUser");
        return resultAdd;
    }

    @Override
    public int removeProductUser(ShoppingCart shoppingCart) {
        logger.trace("Start method JdbsShoppingCartDAO removeProductUser");

        int resultRemove = 0;
        String sql = "DELETE FROM shopping_cart WHERE user_id = ? AND product_id = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, shoppingCart.getUserId());
            preparedStatement.setInt(2, shoppingCart.getProductId());

            resultRemove = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Failed to remove product to cart!!!");
        }

        logger.trace("End method JdbsShoppingCartDAO removeProductUser");
        return resultRemove;
    }

    @Override
    public List<ShoppingCart> getAllProductsUser(int userId) {
        logger.trace("Start method JdbsShoppingCartDAO getAllProductsUser");

        List<ShoppingCart> listProductId = new ArrayList<>();
        String sql = "SELECT user_id, product_id FROM shopping_cart WHERE user_id = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = preparedStatement(connection, sql, userId);
                ResultSet resultSet = preparedStatement.executeQuery()

        ){
            while (resultSet.next()){
                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.setUserId(resultSet.getInt("user_id"));
                shoppingCart.setProductId(resultSet.getInt("product_id"));

                listProductId.add(shoppingCart);
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
