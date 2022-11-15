package org.example.dao.jdbc;

import org.example.dao.UserDetailsDAO;
import org.example.entity.UserDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserDetailsDAOTest {
    private UserDetailsDAO userDetailsDAO = new JdbcUserDetailsDAO();
    @Test
    void add() {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserDetailsId(1);
        userDetails.setLastName("Stanley");
        userDetails.setAge(25);
        userDetails.setCity("London");

        int expected = 0;
        int actual = userDetailsDAO.add(userDetails);

        assertEquals(expected, actual);
    }

    @Test
    void getById() {
        UserDetails expectedUserDetails = new UserDetails();

        expectedUserDetails.setUserDetailsId(1);
        expectedUserDetails.setLastName("Stanley");
        expectedUserDetails.setAge(25);
        expectedUserDetails.setCity("London");

        UserDetails actualUserDetails = userDetailsDAO.getById(1);

        assertEquals(expectedUserDetails, actualUserDetails);
    }

    @Test
    void update() {
        UserDetails userDetails = new UserDetails();

        userDetails.setUserDetailsId(1);
        userDetails.setLastName("Stanley");
        userDetails.setAge(25);
        userDetails.setCity("London");

        int expected = 1;
        int actual = userDetailsDAO.update(userDetails);

        assertEquals(expected, actual);
    }

    @Test
    void remove() {
        UserDetails userDetails = new UserDetails();

        userDetails.setUserDetailsId(0);
        userDetails.setLastName("Stanley");
        userDetails.setAge(25);
        userDetails.setCity("London");

        int expected = 0;
        int actual = userDetailsDAO.remove(userDetails);

        assertEquals(expected, actual);
    }
}