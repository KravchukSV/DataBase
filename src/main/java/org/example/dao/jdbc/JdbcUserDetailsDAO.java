package org.example.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.bl.Util;
import org.example.dao.UserDetailsDAO;
import org.example.entity.User;
import org.example.entity.UserDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDetailsDAO extends Util implements UserDetailsDAO {
    private static Logger logger = LogManager.getLogger();

    @Override
    public int add(UserDetails userDetails) {
        logger.trace("Start method JdbsUserDetailsDao add");
        int addResult = 0;
        String sql = "INSERT INTO user_details" +
                     "(user_details_id, last_name, age, city) VALUES(?, ?, ?, ?);";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, userDetails.getUserDetailsId());
            preparedStatement.setString(2, userDetails.getLastName());
            preparedStatement.setInt(3, userDetails.getAge());
            preparedStatement.setString(4, userDetails.getCity());
            addResult = preparedStatement.executeUpdate();

            logger.info("add new User details");

        } catch (SQLException e) {
            logger.error("No new user details added!!!");
            //throw new RuntimeException(e);
        }
        logger.trace("End method JdbsUserDetailsDao add");
        return addResult;
    }

    @Override
    public List<UserDetails> getAll() {
        logger.trace("Start method JdbsUserDetailsDao getAll");
        List<UserDetails> usersDetails = new ArrayList<>();
        String sql = "SELECT * FROM user_details;";
        try(
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while (resultSet.next()){
                UserDetails userDetails = new UserDetails();

                userDetails.setUserDetailsId(resultSet.getInt("user_details_id"));
                userDetails.setLastName(resultSet.getString("last_name"));
                userDetails.setAge(resultSet.getInt("age"));
                userDetails.setCity(resultSet.getString("city"));

                usersDetails.add(userDetails);
            }
            logger.info("get all users details");
        } catch (SQLException e) {
            logger.error("Could not get users details!!!");
            throw new RuntimeException(e);
        }

        logger.trace("End method JdbsUserDetailsDao getAll");
        return usersDetails;
    }

    @Override
    public UserDetails getById(int id) {
        logger.trace("Start method JdbsUserDetailsDao getById");
        UserDetails userDetails = new UserDetails();
        String sql = "SELECT user_details_id, last_name, age, city " +
                "FROM user_details " +
                "WHERE user_details_id = ?;";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = preparedStatementId(connection, sql, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ){

            if(resultSet.next()){
                userDetails.setUserDetailsId(resultSet.getInt("user_details_id"));
                userDetails.setLastName(resultSet.getString("last_name"));
                userDetails.setAge(resultSet.getInt("age"));
                userDetails.setCity(resultSet.getString("city"));
            }
            logger.info("returning the user details by id");

        } catch (SQLException e) {
            logger.error("It was not possible to return the user details by id!!!");
            //throw new RuntimeException(e);
        }

        logger.trace("End method JdbsUserDetailsDao getById");
        return userDetails;
    }

    @Override
    public int update(UserDetails userDetails) {
        logger.trace("Start method JdbsUserDetailsDao update");

        int resultUpdate = 0;

        String sql = "UPDATE user_details " +
                "SET user_details_id = ?, last_name = ?, age = ?, city = ? " +
                "WHERE user_details_id = ?;";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, userDetails.getUserDetailsId());
            preparedStatement.setString(2, userDetails.getLastName());
            preparedStatement.setInt(3, userDetails.getAge());
            preparedStatement.setString(4, userDetails.getCity());
            preparedStatement.setInt(5, userDetails.getUserDetailsId());

            logger.info("updating user details information");

            resultUpdate = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Failed to update user details information!!!");
            //throw new RuntimeException(e);
        }
        logger.trace("End method JdbsUserDetailsDao update");
        return resultUpdate;
    }

    @Override
    public int remove(UserDetails userDetails) {
        logger.trace("Start method JdbsUserDetailsDao remove");

        int resultRemove = 0;
        String sql = "DELETE FROM user_details WHERE user_details_id = ?;";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, userDetails.getUserDetailsId());

            resultRemove = preparedStatement.executeUpdate();
            logger.info("delete the user details");

        } catch (SQLException e) {
            logger.error("Failed to delete user details!!!");
            //throw new RuntimeException(e);
        }

        logger.trace("End method JdbsUserDetailsDao remove");
        return resultRemove;
    }

    private PreparedStatement preparedStatementId(Connection connection, String sql, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }
}
