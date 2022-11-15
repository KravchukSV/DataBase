package org.example.dao;

import org.example.entity.UserDetails;

import java.util.List;

public interface UserDetailsDAO {
    //create
    int add(UserDetails userDetails);

    //read
    List<UserDetails> getAll();
    UserDetails getById(int id);

    //update
    int update(UserDetails userDetails);

    //delete
    int remove(UserDetails userDetails);
}
