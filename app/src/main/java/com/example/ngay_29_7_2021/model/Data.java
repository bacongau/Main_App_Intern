package com.example.ngay_29_7_2021.model;

public class Data {
    private String customerID,fullName,avatarUrl,email;

    public Data(String customerID, String fullName, String avatarUrl, String email) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.email = email;
    }

    public Data() {
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
