package org.example.dao.hibernate;

import org.example.dao.UserDAO;
import org.example.entity.User;
import org.example.entity.UserDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUserDAOTest {
    private UserDAO userDAO = new HibernateUserDAO();

    @Test
    void remove() {
        User user = getUser();

        int actual = userDAO.remove(user);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        User user = getUser();

        int actual = userDAO.update(user);
        int expected = 0;

        assertEquals(expected, actual);


    }

    @Test
    void getById() {
        User expectedUser = getUser();
        expectedUser.setUserId(1);

        User actualUser = userDAO.getById(1);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void add() {
        User user = getUser();
        user.setUserId(1);

        int actual = userDAO.add(user);
        int expected = 0;

        assertEquals(expected, actual);
    }

    private User getUser(){
        UserDetails userDetails = new UserDetails();
        userDetails.setUserDetailsId(1);
        userDetails.setCity("test");
        userDetails.setLastName("test");
        userDetails.setAge(0);

        User user = new User();
        user.setUserId(0);
        user.setName("Black");
        user.setUserDetails(userDetails);

        return user;
    }
}