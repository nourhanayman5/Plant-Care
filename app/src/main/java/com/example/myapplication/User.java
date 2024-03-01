package com.example.myapplication;

import android.app.Application;

public class User extends Application {
    private String username;
    private String email;

    public String getUserName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
