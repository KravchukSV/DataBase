package org.example.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.util.UtilJDBC;
import org.example.dao.OrderDAO;
import org.example.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbsOrderDAO extends UtilJDBC implements OrderDAO {

    private static Logger logger = LogManager.getLogger();

    @Override
    public int addOrder(Order order) {
        logger.trace("Start method JdbsOrderDao add");
        int addResult = 0;
        String sql = "INSERT INTO orders" +
                "(list_product, order_price, user_id, order_date) VALUES(?, ?, ?, ?);";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, order.getListProduct());
            preparedStatement.setInt(2, order.getOrderPrice());
            preparedStatement.setInt(3, order.getUserId());
            preparedStatement.setDate(4, order.getOrderDate());

            addResult = preparedStatement.executeUpdate();

            logger.info("add new order");

        } catch (SQLException e) {
            logger.error("No new order added!!!");
            //throw new RuntimeException(e);
        }
        logger.trace("End method JdbsUserDao add");
        return addResult;
    }

    @Override
    public List<Order> getOrdersUser(int userId) {
        logger.trace("Start method JdbsOrderDao getOrders");
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?;";
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = preparedStatement(connection, sql, userId);
                ResultSet resultSet = preparedStatement.executeQuery()

        ) {
            while (resultSet.next()){
                Order order = new Order();

                order.setOrderId(resultSet.getInt("order_id"));
                order.setListProduct(resultSet.getString("list_product"));
                order.setOrderPrice(resultSet.getInt("order_price"));
                order.setUserId(resultSet.getInt("user_id"));
                order.setOrderDate(resultSet.getDate("order_date"));

                orders.add(order);
            }
            logger.info("get all orders user");
        } catch (SQLException e) {
            logger.error("Could not get orders user!!!");
            throw new RuntimeException(e);
        }

        logger.trace("End method JdbsOrderDao getOrdersUser");
        return orders;
    }

    @Override
    public List<Order> getAllOrder() {
        logger.trace("Start method JdbsOrderDao getAllOrders");
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders;";
        try(
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)

        ) {
            while (resultSet.next()){
                Order order = new Order();

                order.setOrderId(resultSet.getInt("order_id"));
                order.setListProduct(resultSet.getString("list_product"));
                order.setOrderPrice(resultSet.getInt("order_price"));
                order.setUserId(resultSet.getInt("user_id"));
                order.setOrderDate(resultSet.getDate("order_date"));

                orders.add(order);
            }
            logger.info("get all orders");
        } catch (SQLException e) {
            logger.error("Could not get all orders!!!");
            throw new RuntimeException(e);
        }

        logger.trace("End method JdbsOrderDao getAllOrders");
        return orders;
    }

    private PreparedStatement preparedStatement(Connection connection, String sql, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);

        return preparedStatement;
    }
}
