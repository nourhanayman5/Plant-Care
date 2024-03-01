package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = (User) getApplicationContext();
        TextView name=(TextView)findViewById(R.id.name);
        TextView email=(TextView)findViewById(R.id.email);

        TextView changephoto=(TextView)findViewById(R.id.change_photo);
        TextView changpass=(TextView)findViewById(R.id.change_password);
        TextView logout=(TextView)findViewById(R.id.logout);
        TextView favorite=(TextView)findViewById(R.id.favoritview);
        DatabaseClass databaseClass = new DatabaseClass(getApplicationContext());
        name.setText(user.getUserName());
        email.setText(user.getEmail());
        changephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,Change_Photo.class);
                startActivity(intent);
            }
        });

        changpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Profile.this,Change_Password.class);
                startActivity(intent2);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(Profile.this,MainActivity.class);
                startActivity(logout);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                Intent i1 = new Intent(Profile.this,Home.class);
                startActivity(i1);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_cam) {
                Intent i3 = new Intent(Profile.this,Identification.class);
                startActivity(i3);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_fav) {
                Intent i2 = new Intent(Profile.this,Favorite.class);
                startActivity(i2);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_profile) {

                return true;
            }
            return false;
        });
    }
}