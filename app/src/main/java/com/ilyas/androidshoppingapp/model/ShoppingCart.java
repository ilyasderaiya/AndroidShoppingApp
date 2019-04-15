package com.ilyas.androidshoppingapp.model;

public class ShoppingCart {

    private int cartId;
    private int productId;
    private int quantity;
    private int dateAdded;
    private String cusID;

    //int ci=c.getArrCart().get(c.getArrCart().size()-1).getCartId();

    public ShoppingCart(){
        //empty constructor
    }
    public ShoppingCart(int cartId, int productId, int quantity, int dateAdded, String cusID) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
        this.cusID = cusID;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(int dateAdded) {
        this.dateAdded = dateAdded;
    }
}
