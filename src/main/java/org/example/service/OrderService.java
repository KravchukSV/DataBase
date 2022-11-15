package org.example.service;

import org.example.dao.FactoryDAO;
import org.example.entity.Order;
import org.example.entity.Product;
import org.example.entity.User;

import java.sql.Date;
import java.util.List;

public class OrderService {
    private FactoryDAO factoryDAO;

    public OrderService(FactoryDAO factoryDAO){
        this.factoryDAO = factoryDAO;
    }

    public void toOrder(User user){
        List<Integer> idProducts = factoryDAO.getShoppingCartDAO().getAllProductsUser(user.getUserId());

        if(idProducts.size() > 0){
            Order userOrder = createOrder(idProducts, user.getUserId());

            factoryDAO.getOrderDAO().addOrder(userOrder);
            factoryDAO.getShoppingCartDAO().removeAllProductsUser(user.getUserId());
        }
    }

    private Order createOrder(List<Integer> idProduct, int user_id){
        String list = "";
        int sumOrder = 0;

        for(int id : idProduct){
            Product product = factoryDAO.getProductDAO().getById(id);
            list += product.getName() + ", ";
            sumOrder += product.getPrice();
        }

        Order order = new Order();
        order.setListProduct(list);
        order.setOrderPrice(sumOrder);
        order.setUserId(user_id);
        order.setOrderDate(new Date(System.currentTimeMillis()));

        return order;
    }
}
