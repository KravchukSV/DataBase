package org.example.dao;

import org.example.entity.Product;

import java.util.List;

public interface ProductDAO {
    //create
    int add(Product product);

    //read
    List<Product> getAll();
    Product getById(int id);

    //update
    int update(Product product);

    //delete
    int remove(Product product);
}
