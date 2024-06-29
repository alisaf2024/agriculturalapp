package com.example.agriculturalapp;

import android.widget.EditText;

public class Order {
    private String details;
    private String price;
    private String userId;
    private String id_Account;
    private String id_Date;
    private String id_CVV;
    private String Order_UsdrID;


    public Order() {
    }

    public Order(String details, String price, String userId, String id_Account, String id_Date, String id_CVV, String order_UsdrID) {
        this.details = details;
        this.price = price;
        this.userId = userId;
        this.id_Account = id_Account;
        this.id_Date = id_Date;
        this.id_CVV = id_CVV;
        this.Order_UsdrID = order_UsdrID;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId_Account() {
        return id_Account;
    }

    public void setId_Account(String id_Account) {
        this.id_Account = id_Account;
    }

    public String getId_Date() {
        return id_Date;
    }

    public void setId_Date(String id_Date) {
        this.id_Date = id_Date;
    }

    public String getId_CVV() {
        return id_CVV;
    }

    public void setId_CVV(String id_CVV) {
        this.id_CVV = id_CVV;
    }

    public String getOrder_UsdrID() {
        return Order_UsdrID;
    }

    public void setOrder_UsdrID(String order_UsdrID) {
        Order_UsdrID = order_UsdrID;
    }
}