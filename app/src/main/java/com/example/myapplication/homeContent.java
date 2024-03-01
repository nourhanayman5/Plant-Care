package com.example.myapplication;

public class homeContent {
    private String plantname;
    private int plantimage;

    public homeContent () {

    }
    public homeContent(String plantName, int imageByteArray) {
        this.plantname = plantName;
        this.plantimage=imageByteArray;
    }

//    public homeContent(String plantName, byte[] bytes) {
//    }

    public String getPlantname() {
        return plantname;
    }

    public void setPlantname(String plantname) {
        this.plantname = plantname;
    }

    public int getPlantimage() {
        return plantimage;
    }

    public void setPlantimage(int plantimage) {
        this.plantimage = plantimage;
    }
}
