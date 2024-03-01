package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;


public class Favorite extends AppCompatActivity {
    RecyclerView recyclerView;
    List<FavContent> dataList;
    FavAdapter adapter;
    FavDB plant;
    User user;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        user = (User) getApplicationContext();
        String u_name = user != null ? user.getUserName() : "";
        recyclerView = findViewById(R.id.plantview1);
        dataList = new ArrayList<>();
        plant = new FavDB(this);

        dataList = plant.getAllData(u_name);
        adapter = new FavAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_fav);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                Intent i1 = new Intent(Favorite.this,Home.class);
                startActivity(i1);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_cam) {
                Intent i2 = new Intent(Favorite.this,Identification.class);
                startActivity(i2);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_fav) {

                return true;
            } else if (itemId == R.id.bottom_profile) {
                Intent i3 = new Intent(Favorite.this,Profile.class);
                startActivity(i3);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }

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


    public static Bitmap drawableToBitmap(Context context, int drawableId) {
        // Get the Drawable from the resource
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        // Check if the drawable is not null
        if (drawable == null) {
            return null;
        }

        // If the drawable is a BitmapDrawable, simply extract and return the Bitmap
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // Otherwise, if the drawable is not a BitmapDrawable, create a new Bitmap and draw the drawable on it
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}