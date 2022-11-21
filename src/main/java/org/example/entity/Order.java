package org.example.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "list_product")
    private String listProduct;

    @Column(name = "order_price")
    private int orderPrice;

    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;


    @Column(name = "order_date")
    private Date orderDate;

    public Order(){
        user = new User();
    }

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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getOrderId() != order.getOrderId()) return false;
        if (getOrderPrice() != order.getOrderPrice()) return false;
        if (getUser().getUserId() != order.getUser().getUserId()) return false;
        if (getListProduct() != null ? !getListProduct().equals(order.getListProduct()) : order.getListProduct() != null)
            return false;
        return getOrderDate() != null ? getOrderDate().equals(order.getOrderDate()) : order.getOrderDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getOrderId();
        result = 31 * result + (getListProduct() != null ? getListProduct().hashCode() : 0);
        result = 31 * result + getOrderPrice();
        result = 31 * result + getUser().getUserId();
        result = 31 * result + (getOrderDate() != null ? getOrderDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", listProduct='" + listProduct + '\'' +
                ", orderPrice=" + orderPrice +
                ", userId=" + user.getUserId() +
                ", orderDate=" + orderDate +
                '}';
    }
}
