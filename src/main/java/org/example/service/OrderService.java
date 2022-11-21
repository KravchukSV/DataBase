package org.example.service;

import org.example.dao.FactoryDAO;
import org.example.entity.Order;
import org.example.entity.Product;
import org.example.entity.ShoppingCart;
import org.example.entity.User;

import java.sql.Date;
import java.util.List;

public class OrderService {
    private FactoryDAO factoryDAO;

    public OrderService(FactoryDAO factoryDAO){
        this.factoryDAO = factoryDAO;
    }

    public void toOrder(User user){
        List<ShoppingCart> shoppingCartList = factoryDAO.getShoppingCartDAO().getAllProductsUser(user.getUserId());

        if(shoppingCartList.size() > 0){
            Order userOrder = createOrder(shoppingCartList, user);

            factoryDAO.getOrderDAO().addOrder(userOrder);
            factoryDAO.getShoppingCartDAO().removeAllProductsUser(user.getUserId());
        }
    }

    private Order createOrder(List<ShoppingCart> shoppingCartList, User user){
        String list = "";
        int sumOrder = 0;

        for(ShoppingCart shoppingCart : shoppingCartList){
            Product product = factoryDAO.getProductDAO().getById(shoppingCart.getProductId());
            list += product.getName() + ", ";
            sumOrder += product.getPrice();
        }

        Order order = new Order();
        order.setListProduct(list);
        order.setOrderPrice(sumOrder);
        order.setUser(user);
        order.setOrderDate(new Date(System.currentTimeMillis()));

        return order;
    }
}
