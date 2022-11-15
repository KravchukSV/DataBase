package org.example.entity;

import java.sql.Date;

public class Order {
    private int orderId;
    private String listProduct;
    private int orderPrice;
    private int userId;
    private Date orderDate;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getListProduct() {
        return listProduct;
    }

    public void setListProduct(String listProduct) {
        this.listProduct = listProduct;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getOrderId() != order.getOrderId()) return false;
        if (getOrderPrice() != order.getOrderPrice()) return false;
        if (getUserId() != order.getUserId()) return false;
        if (getListProduct() != null ? !getListProduct().equals(order.getListProduct()) : order.getListProduct() != null)
            return false;
        return getOrderDate() != null ? getOrderDate().equals(order.getOrderDate()) : order.getOrderDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getOrderId();
        result = 31 * result + (getListProduct() != null ? getListProduct().hashCode() : 0);
        result = 31 * result + getOrderPrice();
        result = 31 * result + getUserId();
        result = 31 * result + (getOrderDate() != null ? getOrderDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", listProduct='" + listProduct + '\'' +
                ", orderPrice=" + orderPrice +
                ", userId=" + userId +
                ", orderDate=" + orderDate +
                '}';
    }
}
