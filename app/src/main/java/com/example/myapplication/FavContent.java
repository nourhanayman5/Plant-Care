package com.example.myapplication;

public class FavContent {
    private String p_name;
    private byte [] p_image;
    private String Fav_user ;

    public FavContent() {
    }

    public FavContent(String p_name,byte [] P_image,String Fav_user) {
        this.p_image = P_image;
        this.p_name = p_name ;
        this.Fav_user = Fav_user;
    }
    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public byte[] getP_image() {
        return p_image;
    }

    public void setP_image(byte[] p_image) {
        this.p_image = p_image;
    }

    public String getFav_user() {
        return Fav_user;
    }

    public void setFav_user(String fav_user) {
        Fav_user = fav_user;
    }
}
