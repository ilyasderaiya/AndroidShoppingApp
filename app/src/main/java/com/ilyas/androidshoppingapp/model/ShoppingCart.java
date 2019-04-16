package com.ilyas.androidshoppingapp.model;

public class ShoppingCart {

    private int cartId;
    private int productId;
    private int quantity;
    private int dateAdded;
    private String productName;
    private String cusID;
    private String imgUrl;
    private Double price;


    //int ci=c.getArrCart().get(c.getArrCart().size()-1).getCartId();

    public ShoppingCart(){
        this.cartId = 0;
        this.productId = 0;
        this.quantity = 0;
        this.dateAdded = 0;
        this.price=0.0;
        this.cusID = "";
        this.productName="";
        this.imgUrl="";
    }

    public ShoppingCart(int cartId, int productId, int quantity, int dateAdded, String productName, String cusID, String imgUrl, Double price) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
        this.productName = productName;
        this.cusID = cusID;
        this.imgUrl=imgUrl;
        this.price = price;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void addCartItem(int productId, int quantity, String productName, String imgUrl, Double price){
        this.productId=productId;
        this.quantity=quantity;
        this.price=price;
        this.productName=productName;
        this.imgUrl=imgUrl;
        System.out.println("Item added");
        viewCartDetails();
    }
    public void viewCartDetails(){
        System.out.println("Name:"+this.productName+" "+this.price+" "+this.quantity);
    }
}
