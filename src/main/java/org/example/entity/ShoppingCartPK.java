package org.example.entity;

import java.io.Serializable;

public class ShoppingCartPK implements Serializable {
    private int userId;
    private int productId;

    public ShoppingCartPK(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCartPK)) return false;

        ShoppingCartPK that = (ShoppingCartPK) o;

        if (getUserId() != that.getUserId()) return false;
        return getProductId() == that.getProductId();
    }

    @Override
    public int hashCode() {
        int result = getUserId();
        result = 31 * result + getProductId();
        return result;
    }
}
