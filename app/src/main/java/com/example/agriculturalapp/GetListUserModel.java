package com.example.agriculturalapp;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GetListUserModel implements Parcelable {

    String bookingDate, bookingTime, getCurrentUserId, userEmail, userMarital, userName,userPhone,userReligion, documentId,appointmentType,selectedFood,selectedFoodImgUri;
    private String sTFoodType;
    private String sTFoodQuantity;
    private String sTFoodExpiryDate;
    public GetListUserModel(ArrayList<GetListUserModel> myListArrayListDetail, Context applicationContext) {
    }
    public GetListUserModel(String bookingDate, String bookingTime, String getCurrentUserId, String userEmail, String userMarital, String userName, String userPhone, String userReligion, String documentId, String appointmentType, String selectedFood, String selectedFoodImgUri, String sTFoodType, String sTFoodQuantity, String sTFoodExpiryDate) {
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.getCurrentUserId = getCurrentUserId;
        this.userEmail = userEmail;
        this.userMarital = userMarital;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userReligion = userReligion;
        this.documentId = documentId;
        this.appointmentType = appointmentType;
        this.selectedFood = selectedFood;
        this.selectedFoodImgUri = selectedFoodImgUri;
        this.sTFoodType = sTFoodType;
        this.sTFoodQuantity = sTFoodQuantity;
        this.sTFoodExpiryDate = sTFoodExpiryDate;
    }

    protected GetListUserModel(Parcel in) {
        bookingDate = in.readString();
        bookingTime = in.readString();
        getCurrentUserId = in.readString();
        userEmail = in.readString();
        userMarital = in.readString();
        userName = in.readString();
        userPhone = in.readString();
        userReligion = in.readString();
        documentId = in.readString();
        appointmentType = in.readString();
        selectedFood = in.readString();
        selectedFoodImgUri = in.readString();
        sTFoodType = in.readString();
        sTFoodQuantity = in.readString();
        sTFoodExpiryDate = in.readString();
    }

    public static final Creator<GetListUserModel> CREATOR = new Creator<GetListUserModel>() {
        @Override
        public GetListUserModel createFromParcel(Parcel in) {
            return new GetListUserModel(in);
        }

        @Override
        public GetListUserModel[] newArray(int size) {
            return new GetListUserModel[size];
        }
    };

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getGetCurrentUserId() {
        return getCurrentUserId;
    }

    public void setGetCurrentUserId(String getCurrentUserId) {
        this.getCurrentUserId = getCurrentUserId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMarital() {
        return userMarital;
    }

    public void setUserMarital(String userMarital) {
        this.userMarital = userMarital;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserReligion() {
        return userReligion;
    }

    public void setUserReligion(String userReligion) {
        this.userReligion = userReligion;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
    public String getSelectedFood() {
        return selectedFood;
    }

    public void setSelectedFood(String selectedFood) {
        this.selectedFood = selectedFood;
    }

    public String getSelectedFoodImgUri() {
        return selectedFoodImgUri;
    }

    public void setSelectedFoodImgUri(String selectedFoodImgUri) {
        this.selectedFoodImgUri = selectedFoodImgUri;
    }

    public String getsTFoodType() {
        return sTFoodType;
    }

    public void setsTFoodType(String sTFoodType) {
        this.sTFoodType = sTFoodType;
    }

    public String getsTFoodQuantity() {
        return sTFoodQuantity;
    }

    public void setsTFoodQuantity(String sTFoodQuantity) {
        this.sTFoodQuantity = sTFoodQuantity;
    }

    public String getsTFoodExpiryDate() {
        return sTFoodExpiryDate;
    }

    public void setsTFoodExpiryDate(String sTFoodExpiryDate) {
        this.sTFoodExpiryDate = sTFoodExpiryDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bookingDate);
        parcel.writeString(bookingTime);
        parcel.writeString(getCurrentUserId);
        parcel.writeString(userEmail);
        parcel.writeString(userMarital);
        parcel.writeString(userName);
        parcel.writeString(userPhone);
        parcel.writeString(userReligion);
        parcel.writeString(documentId);
        parcel.writeString(appointmentType);
        parcel.writeString(selectedFood);
        parcel.writeString(selectedFoodImgUri);
        parcel.writeString(sTFoodType);
        parcel.writeString(sTFoodQuantity);
        parcel.writeString(sTFoodExpiryDate);
    }
}
