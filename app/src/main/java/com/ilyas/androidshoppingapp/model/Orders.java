package com.ilyas.androidshoppingapp.model;

import java.util.ArrayList;
import java.util.List;

public class Orders {
    private String address, city, date, key, name, phone, time, total_amount;
    private List<Products> ordered_products;

    public Orders() {
    }

    public Orders(String address, String city, String date, String key, String name, String phone, String time, String total_amount, List<Products> ordered_products) {
        this.address = address;
        this.city = city;
        this.date = date;
        this.key = key;
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.total_amount = total_amount;
        this.ordered_products = ordered_products;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public List<Products> getOrdered_products() {
        return ordered_products;
    }

    public void setOrdered_products(List<Products> ordered_products) {
        this.ordered_products = ordered_products;
    }
}
