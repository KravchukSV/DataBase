package org.example.dao;

import org.example.entity.User;

import java.util.List;

public interface UserDAO {

    //create
    int add(User user);

    //read
    List<User> getAll();
    User getById(int id);

    //update
    int update(User user);

    //delete
    int remove(User user);
}
