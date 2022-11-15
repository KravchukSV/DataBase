package org.example.dao.jdbc;

import org.example.dao.UserDAO;
import org.example.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JdbsUserDAOTest {
    private UserDAO userDAO = new JdbsUserDAO();

    @Test
    void remove() {
        User user = new User();
        user.setUserId(0);
        user.setName("Sam");
        user.setUserDetailsId(1);

        int actual = userDAO.remove(user);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        User user = new User();
        user.setUserId(0);
        user.setName("Sam");
        user.setUserDetailsId(1);

        int actual = userDAO.update(user);
        int expected = 0;

        assertEquals(expected, actual);


    }

    @Test
    void getById() {
        User expectedUser = new User();
        expectedUser.setUserId(1);
        expectedUser.setName("Black");
        expectedUser.setUserDetailsId(1);

        User actualUser = userDAO.getById(1);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void add() {
        User user = new User();
        user.setUserId(1);
        user.setName("Sam");
        user.setUserDetailsId(1);

        int actual = userDAO.add(user);
        int expected = 0;

        assertEquals(expected, actual);
    }
}