package com.ilyas.androidshoppingapp.model;

public class Cart {

    private String key, pname, pprice, quantity, time, date, image;

    public Cart() {
    }

    public Cart(String key, String pname, String pprice, String quantity, String time, String date, String image) {
        this.key = key;
        this.pname = pname;
        this.pprice = pprice;
        this.quantity = quantity;
        this.time = time;
        this.date = date;
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
