package com.ilyas.androidshoppingapp.model

import java.util.ArrayList

class Customer {
    var customerName: String? = null
    var address: String? = null
    var email: String? = null
    var creditCardInfo: String? = null
    var shippingInfo: String? = null
    //int flag = 1;
    //Scanner sc = new Scanner(System.in);
    var arrCart: List<ShoppingCart>? = null
        private set//=new ArrayList<ShoppingCart>();
    var arrOrd: List<Orders>? = null
        private set//=new ArrayList<Orders>();

    private constructor() {
        this.customerName = String()
        this.address = String()
        this.email = String()
        this.creditCardInfo = String()
        this.shippingInfo = String()
        this.arrCart = ArrayList()
        this.arrOrd = ArrayList()
    }

    private constructor(customerName: String, address: String, email: String, creditCardInfo: String, shippingInfo: String, arrCart: ArrayList<ShoppingCart>, arrOrd: ArrayList<Orders>) {
        this.customerName = customerName
        this.address = address
        this.email = email
        this.creditCardInfo = creditCardInfo
        this.shippingInfo = shippingInfo
        this.arrCart = arrCart
        this.arrOrd = arrOrd
    }

    fun setArrCart(arrCart: ArrayList<ShoppingCart>) {
        this.arrCart = arrCart
    }

    fun setArrOrd(arrOrd: ArrayList<Orders>) {
        this.arrOrd = arrOrd
    }

    fun dispInfo() {
        println("Name: " + this.customerName + "\nemail:" + this.email)
    }
}
