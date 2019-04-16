package com.ilyas.androidshoppingapp.model;

public class Products {
    private int productID;
    private String productName;
    private double productPrice;
    private int quantity;
    private String imageUrl;

    public Products(){

    }

    public Products(int productID, String productName, float productPrice, int quantity, String imageUrl) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
