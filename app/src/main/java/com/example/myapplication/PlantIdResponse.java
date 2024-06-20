package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlantIdResponse {
    @SerializedName("id")
    public String id;

    @SerializedName("suggestions")
    public List<Suggestion> suggestions;

    @SerializedName("diseases")
    public List<Disease> diseases;

    public static class Suggestion {
        @SerializedName("plant_name")
        public String plantName;

        @SerializedName("probability")
        public double probability;

        // Add other fields as needed
    }

    public static class Disease {
        @SerializedName("disease_name")
        public String diseaseName;

        @SerializedName("probability")
        public double probability;

        @SerializedName("description")
        public String description;

        @SerializedName("treatment")
        public String treatment;
    }

}
