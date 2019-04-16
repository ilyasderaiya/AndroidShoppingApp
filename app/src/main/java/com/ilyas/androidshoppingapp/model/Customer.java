package com.ilyas.androidshoppingapp.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerName;
    private String address;
    private String email;
    private String creditCardInfo;
    private String shippingInfo;
    //int flag = 1;
    //Scanner sc = new Scanner(System.in);
    private List<ShoppingCart> arrCart;//=new ArrayList<ShoppingCart>();
    private List<Orders> arrOrd;//=new ArrayList<Orders>();
    private static Customer customer=null;
    private Customer(){
        this.customerName = new String();
        this.address = new String();
        this.email = new String();
        this.creditCardInfo = new String();
        this.shippingInfo = new String();
        this.arrCart = new ArrayList<ShoppingCart>();
        this.arrOrd = new ArrayList<Orders>();
    }

    private Customer(String customerName, String address, String email, String creditCardInfo, String shippingInfo, ArrayList<ShoppingCart> arrCart, ArrayList<Orders> arrOrd) {
        this.customerName = customerName;
        this.address = address;
        this.email = email;
        this.creditCardInfo = creditCardInfo;
        this.shippingInfo = shippingInfo;
        this.arrCart = arrCart;
        this.arrOrd = arrOrd;
    }
    public static Customer getInstance(){
        if(customer==null){
            customer=new Customer();
        }
        return customer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCardInfo() {
        return creditCardInfo;
    }

    public void setCreditCardInfo(String creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }

    public String getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(String shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public List<ShoppingCart> getArrCart() {
        return arrCart;
    }

    public void setArrCart(ArrayList<ShoppingCart> arrCart) {
        this.arrCart = arrCart;
    }

    public List<Orders> getArrOrd() {
        return arrOrd;
    }

    public void setArrOrd(ArrayList<Orders> arrOrd) {
        this.arrOrd = arrOrd;
    }
    public void dispInfo(){
        System.out.println("Name: "+this.customerName+"\nemail:"+this.email);
    }
}
