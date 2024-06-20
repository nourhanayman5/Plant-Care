package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class ImageAnalysisResponse {
    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("details")
    private String details;

    // Getters and setters

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
