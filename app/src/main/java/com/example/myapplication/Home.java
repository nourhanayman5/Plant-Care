package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

//    String username;
    RecyclerView recyclerView;
    List<homeContent> dataList;
    plantDB1 plant;
    MyAdapter adapter;
    User user;
    TextView personName;
//
//
    @SuppressLint("NonConstantResourceId")
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.plantview);
        dataList = new ArrayList<>();
        plant = new plantDB1(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        user = (User) getApplicationContext();
        personName = (TextView) findViewById(R.id.person_name);
        personName.setText(user.getUserName());

        String[] plantnames = {"Tomato Healthy", "Potato Healthy","PapperBell Healthy"
                , "Potato Late Blight", "Potato Early Blight","PapperBell Bacterial Spot"
                ,"tomato Bacterial Spot" , "Tomato Early Blight" ,"Tomato Late Blight" , "Tomato Leaf Mold"};

        int[] plantImages = {R.drawable.tomatohealthy, R.drawable.potatohealthy,R.drawable.papperbellhealthy
                , R.drawable.potatolateblight, R.drawable.potatoearlyblight,R.drawable.papperbellbacterialspot
                , R.drawable.tomatobacterialspot, R.drawable.tomatoearlyblight , R.drawable.tomatolateblight,R.drawable.tomatoleafmold};

        for (int i=0 ; i<plantnames.length ; i++) {

            homeContent homeContent = new homeContent(plantnames[i] , plantImages[i]);
            dataList.add(homeContent);

        }
        adapter = new MyAdapter(this, dataList);
        recyclerView.setAdapter(adapter);


        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                return true;
            } else if (itemId == R.id.bottom_cam) {
                Intent i1 = new Intent(Home.this, Identification.class);
                startActivity(i1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_fav) {
                Intent i2 = new Intent(Home.this, Favorite.class);
                startActivity(i2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_profile) {
                Intent i3 = new Intent(Home.this, Profile.class);
                startActivity(i3);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

    }
//
//
    public byte[] bitmapToByteArray(Context context, int resourceId) {
        // Decode the image from resources into a Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

        // Convert the Bitmap to a byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Return the byte array
        return byteArray;


    }
}



