package org.example.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.bl.Util;
import org.example.dao.ProductDAO;
import org.example.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDAO extends Util implements ProductDAO {
    private static Logger logger = LogManager.getLogger();

    @Override
    public int add(Product product) {
        logger.trace("Start method JdbsProductDao add");
        int addResult = 0;
        String sql = "INSERT INTO products(product_id, name, weight, price) VALUES(?, ?, ?, ?);";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, product.getProductId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getWeight());
            preparedStatement.setInt(4, product.getPrice());
            addResult = preparedStatement.executeUpdate();

            logger.info("add new Product");

        } catch (SQLException e) {
            logger.error("No new product added!!!");
            //throw new RuntimeException(e);
        }
        logger.trace("End method JdbsProductDao add");
        return addResult;
    }

    @Override
    public List<Product> getAll() {
        logger.trace("Start method JdbsproductDao getAll");
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products;";
        try(
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while (resultSet.next()){
                Product product = new Product();

                product.setProductId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setWeight(resultSet.getDouble("weight"));
                product.setPrice(resultSet.getInt("price"));

                products.add(product);
            }
            logger.info("get all products");
        } catch (SQLException e) {
            logger.error("Could not get products!!!");
            throw new RuntimeException(e);
        }

        logger.trace("End method JdbsProductDao getAll");
        return products;
    }

    @Override
    public Product getById(int id) {
        logger.trace("Start method JdbsProductDao getById");
        Product product = new Product();
        String sql = "SELECT product_id, name, weight, price " +
                "FROM products " +
                "WHERE product_id = ?;";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = preparedStatementId(connection, sql, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ){

            if(resultSet.next()){
                product.setProductId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setWeight(resultSet.getDouble("weight"));
                product.setPrice(resultSet.getInt("price"));
            }
            logger.info("returning the product by id");

        } catch (SQLException e) {
            logger.error("It was not possible to return the product by id!!!");
            //throw new RuntimeException(e);
        }

        logger.trace("End method JdbsProductDao getById");
        return product;
    }

    @Override
    public int update(Product product) {
        logger.trace("Start method JdbsProductDao update");

        int resultUpdate = 0;

        String sql = "UPDATE products " +
                "SET product_id = ?, name = ?, weight = ?, price = ? " +
                "WHERE product_id = ?;";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, product.getProductId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getWeight());
            preparedStatement.setInt(4, product.getPrice());
            preparedStatement.setInt(5, product.getProductId());
            logger.info("updating product information");

            resultUpdate = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Failed to update product information!!!");
            //throw new RuntimeException(e);
        }
        logger.trace("End method JdbsProductDao update");
        return resultUpdate;
    }

    @Override
    public int remove(Product product) {
        logger.trace("Start method JdbsProductDao remove");

        int resultRemove = 0;
        String sql = "DELETE FROM products WHERE product_id = ?;";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, product.getProductId());

            resultRemove = preparedStatement.executeUpdate();
            logger.info("delete the product");

        } catch (SQLException e) {
            logger.error("Failed to delete product!!!");
            //throw new RuntimeException(e);
        }

        logger.trace("End method JdbsProductDao remove");
        return resultRemove;
    }

    private PreparedStatement preparedStatementId(Connection connection, String sql, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }
}
