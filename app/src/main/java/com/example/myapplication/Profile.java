package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class Profile extends AppCompatActivity {
    Button change_photo,change_pass;
    ImageView profile_image;
    Uri selectedImageUri;
    Bitmap bitmap = null;
    DatabaseClass DB;
//    String user;
    String u;
//    User useremail ;
    User user;
    String u_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


//        useremail= (User) getApplicationContext();
//        user = useremail.getEmail();
//        change_photo=(Button) findViewById(R.id.change_pro_photo);
//        change_pass=(Button) findViewById(R.id.change_password);
//        profile_image=(ImageView)findViewById(R.id.profile_photo);
        u_name = user != null ? user.getEmail() : "";
        DB = new DatabaseClass(this);


        user = (User) getApplicationContext();
        profile_image = (ImageView) findViewById(R.id.profile_photo2);
        TextView name=(TextView)findViewById(R.id.name);
        TextView email=(TextView)findViewById(R.id.email);

//        TextView changephoto=(TextView)findViewById(R.id.change_photo);
        TextView changpass=(TextView)findViewById(R.id.change_password);
        TextView logout=(TextView)findViewById(R.id.logout);
        TextView favorite=(TextView)findViewById(R.id.favoritview);
        DatabaseClass databaseClass = new DatabaseClass(getApplicationContext());
        name.setText(user.getUserName());
        email.setText(user.getEmail());


//        bitmap=DB.getImageProfileFromDatabase(u_name);
//        profile_image.setImageBitmap(bitmap);


        profile_image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                chooseProfilePicture();
                bitmap=DB.getImageProfileFromDatabase(u_name);
                profile_image.setImageBitmap(bitmap);
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

    private void chooseProfilePicture() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_edit_profile, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        AlertDialog alertDialogProfile = builder.create();
        alertDialogProfile.show();
        ImageView camera = dialogView.findViewById(R.id.camera);
        ImageView gallery = dialogView.findViewById(R.id.galary);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCameraPermission()) {
                    pickfromcamera();
                    alertDialogProfile.cancel();
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickfromgallery();
                alertDialogProfile.cancel();
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && requestCode == 1) {

                    selectedImageUri = data.getData();
                    try{
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);

                        profile_image.setImageBitmap(bitmap);
                        DB.updatePhoto(u_name,bitmap);
                        Toast.makeText(getApplicationContext(),"changed Successfullly",Toast.LENGTH_LONG).show();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    bitmap = (Bitmap) bundle.get("data");
                    profile_image.setImageBitmap(bitmap);
                    DB.updatePhoto(u_name,bitmap);
                    Toast.makeText(getApplicationContext(),"changed Successfullly",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    private boolean checkCameraPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            boolean res1 = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED;
            if(res1){
                ActivityCompat.requestPermissions(Profile.this, new String[]{android.Manifest.permission.CAMERA} , 20);
                return false;
            }
        }
        return  true;
    }

    private void pickfromgallery() {
        Intent pickphoto = new Intent();
        pickphoto.setType("image/*");
        pickphoto.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser( pickphoto, "Sellect picture"), 1);


    }

    @SuppressLint("QueryPermissionsNeeded")
    private void pickfromcamera() {
        Intent pickphoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pickphoto.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pickphoto, 2);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            pickfromcamera();
        }
        else
            Toast.makeText(Profile.this , "Permission not Granted" , Toast.LENGTH_SHORT).show();
    }

}