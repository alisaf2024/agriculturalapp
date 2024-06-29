package com.example.agriculturalapp;

public class UserDataModel {
    String firstname;
    String email;
    String password;
    String phone;
    String religion;
    String meritalstatus;
    String userId;
    String id;
    String appointmentType;
    String userType;

    public UserDataModel() {
    }

    public UserDataModel(String firstname, String email, String password, String phone, String religion, String meritalstatus, String userId, String id , String appointmentType, String userType) {
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.religion = religion;
        this.meritalstatus = meritalstatus;
        this.userId = userId;
        this.id = id;
        this.appointmentType = appointmentType;
        this.userType = userType;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getMeritalstatus() {
        return meritalstatus;
    }

    public void setMeritalstatus(String meritalstatus) {
        this.meritalstatus = meritalstatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setAllUser(String userName, String userEmail, String userPhone,  String documentId) {
        this.firstname = userName;
        this.email = userEmail;
        this.phone = userPhone;
        this.userId = documentId;

    }
}
