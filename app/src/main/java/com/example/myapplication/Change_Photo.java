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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class Change_Photo extends AppCompatActivity {
    Button change_photo,change_pass;
    ImageView profile_image;
    Uri selectedImageUri;
    Bitmap bitmap = null;
    DatabaseClass DB;
    String user;
    User useremail ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_photo);
//        Intent i=getIntent();
//        user=i.getStringExtra("username");


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        useremail= (User) getApplicationContext();
        user = useremail.getEmail();
        change_photo=(Button) findViewById(R.id.change_pro_photo);
//        change_pass=(Button) findViewById(R.id.change_password);
        profile_image=(ImageView)findViewById(R.id.profile_photo);
        DB = new DatabaseClass(this);
        bitmap=DB.getImageProfileFromDatabase(user);
        profile_image.setImageBitmap(bitmap);
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent change_p=new Intent(Change_Photo.this,Change_Password.class);
//                change_p.putExtra("username", user);
                startActivity(change_p);
            }
        });
        change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null && user != null) {
                    DB.change_profile_photo(bitmap,"n@gmail.com");
                    Toast.makeText(Change_Photo.this, "Changed successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("Change_Photo", "Bitmap or user is null");
                    // Handle null values gracefully, e.g., show an error message to the user
                    Toast.makeText(Change_Photo.this, "Unable to change profile photo", Toast.LENGTH_SHORT).show();
                }

            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                chooseProfilePicture();
            }
        });

    }
    private void chooseProfilePicture() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Change_Photo.this);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && requestCode == 1) {

                    selectedImageUri = data.getData();
                    try{
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);

                        profile_image.setImageBitmap(bitmap);
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

                }
                break;
        }
    }


    private boolean checkCameraPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            boolean res1 = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED;
            if(res1){
                ActivityCompat.requestPermissions(Change_Photo.this, new String[]{android.Manifest.permission.CAMERA} , 20);
                return false;
            }
        }
        return  true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            pickfromcamera();
        }
        else
            Toast.makeText(Change_Photo.this , "Permission not Granted" , Toast.LENGTH_SHORT).show();
    }



}

