package org.example.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.bl.Util;
import org.example.dao.UserDAO;
import org.example.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbsUserDAO extends Util implements UserDAO {
    private static Logger logger = LogManager.getLogger();

    @Override
    public int add(User user) {
        logger.trace("Start method JdbsUserDao add");
        int addResult = 0;
        String sql = "INSERT INTO users(user_id, name, user_details_id) VALUES(?, ?, ?);";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getUserDetailsId());
            addResult = preparedStatement.executeUpdate();

            logger.info("add new User");

        } catch (SQLException e) {
            logger.error("No new user added!!!");
            //throw new RuntimeException(e);
        }
        logger.trace("End method JdbsUserDao add");
        return addResult;
    }

    @Override
    public List<User> getAll() {
        logger.trace("Start method JdbsUserDao getAll");
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users;";
        try(
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while (resultSet.next()){
                User user = new User();

                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setUserDetailsId(resultSet.getInt("user_details_id"));

                users.add(user);
            }
            logger.info("get all users");
        } catch (SQLException e) {
            logger.error("Could not get users!!!");
            throw new RuntimeException(e);
        }

        logger.trace("End method JdbsUserDao getAll");
        return users;
    }

    @Override
    public User getById(int id) {
        logger.trace("Start method JdbsUserDao getById");
        User user = new User();
        String sql = "SELECT user_id, name, user_details_id " +
                     "FROM users " +
                     "WHERE user_id = ?;";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = preparedStatementId(connection, sql, id);
                ResultSet resultSet = preparedStatement.executeQuery()
                ){

            if(resultSet.next()){
                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setUserDetailsId(resultSet.getInt("user_details_id"));
            }
            logger.info("returning the user by id");

        } catch (SQLException e) {
            logger.error("It was not possible to return the user by id!!!");
            //throw new RuntimeException(e);
        }

        logger.trace("End method JdbsUserDao getById");
        return user;
    }

    @Override
    public int update(User user) {
        logger.trace("Start method JdbsUserDao update");

        int resultUpdate = 0;

        String sql = "UPDATE users " +
                     "SET user_id = ?, name = ?, user_details_id = ? " +
                     "WHERE user_id = ?;";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getUserDetailsId());
            preparedStatement.setInt(4, user.getUserId());
            logger.info("updating user information");

            resultUpdate = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Failed to update user information!!!");
            //throw new RuntimeException(e);
        }
        logger.trace("End method JdbsUserDao update");
        return resultUpdate;
    }

    @Override
    public int remove(User user) {
        logger.trace("Start method JdbsUserDao remove");

        int resultRemove = 0;
        String sql = "DELETE FROM users WHERE user_id = ?;";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, user.getUserId());

            resultRemove = preparedStatement.executeUpdate();
            logger.info("delete the user");

        } catch (SQLException e) {
            logger.error("Failed to delete user!!!");
            //throw new RuntimeException(e);
        }

        logger.trace("End method JdbsUserDao remove");
        return resultRemove;
    }

    private PreparedStatement preparedStatementId(Connection connection, String sql, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }
}
