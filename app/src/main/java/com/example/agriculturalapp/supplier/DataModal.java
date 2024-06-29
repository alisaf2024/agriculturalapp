package com.example.agriculturalapp.supplier;

public class DataModal {
    // variables for storing our image and name.
    private String selectedSection;
    private String source;
    private String Details;
    private String Price;
    private String userId;
    private String id;
    private String Remarks;
    private String imageURL;
    private String dataKey;

    public DataModal() {
    }

    public DataModal(String selectedSection, String source, String details, String price, String userId, String id, String remarks, String imageURL, String dataKey) {
        this.selectedSection = selectedSection;
        this.source = source;
        Details = details;
        Price = price;
        this.userId = userId;
        this.id = id;
        Remarks = remarks;
        this.imageURL = imageURL;
        this.dataKey = dataKey;
    }

    public String getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(String selectedSection) {
        this.selectedSection = selectedSection;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
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

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
}
