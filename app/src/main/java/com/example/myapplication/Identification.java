package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ml.ConvertedModel1;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Identification extends AppCompatActivity {
    Button predictBtn;
    Button favorite;
    TextView result;
    ImageView productImage;
    Uri selectedImageUri;
    Bitmap bitmap = null;
    User user;
    FavDB plant ;
    String p_name;
    String []class_names ={"Tomato Bacterial spot","Tomato mosaic virus","Tomato Spider mites Two spotted spider mite",
            "Pepperbell Bacterial spot","Potato Late blight","Pepperbell healthy","Potato Early blight",
            "Tomato Late blight","Tomato YellowLea Curl Virus","Tomato Target Spot","Tomato Early blight",
            "Potato healthy","Tomato Septoria leaf spot","Tomato healthy","Tomato Leaf Mold"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        predictBtn = findViewById(R.id.predictBtn);
        result = findViewById(R.id.result);
        productImage = findViewById(R.id.image);
        favorite = (Button) findViewById(R.id.favorite);
        user = (User) getApplicationContext();
        String u_name = user != null ? user.getUserName() : "";
        plant = new FavDB(getApplicationContext());
        productImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                chooseProfilePicture();
            }

        });
        predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ConvertedModel1 model = ConvertedModel1.newInstance(Identification.this);

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 64, 64, 3}, DataType.FLOAT32);
                    bitmap=Bitmap.createScaledBitmap(bitmap,64,64,false);
//                    inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 64 * 64 * 3);
                    byteBuffer.order(ByteOrder.nativeOrder());

                    int[] intValues = new int[64 * 64];
                    bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
                    int pixel = 0;
                    //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
                    for(int i = 0; i < 64; i ++){
                        for(int j = 0; j < 64; j++){
                            int val = intValues[pixel++]; // RGB
                            byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                            byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                            byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                        }
                    }

                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    ConvertedModel1.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                    p_name=class_names[getMax(outputFeature0.getFloatArray())];
                    result.setText(class_names[getMax(outputFeature0.getFloatArray())]+" ");

                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {
                    // TODO Handle the exception
                }



            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plant.insertFav(p_name,u_name,bitmap);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_cam);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                Intent i1 = new Intent(Identification.this,Home.class);
                startActivity(i1);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_cam) {

                return true;
            } else if (itemId == R.id.bottom_fav) {
                Intent i2 = new Intent(Identification.this,Favorite.class);
                startActivity(i2);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_profile) {
                Intent i3 = new Intent(Identification.this,Profile.class);
                startActivity(i3);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }
    int getMax(float[]arr){
        int max=0;
        for (int i=0;i<arr.length;i++){
            if (arr[i]>arr[max]){
                max=i;
            }
        }
        return max;

    }
    private void chooseProfilePicture() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Identification.this);
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

                        productImage.setImageBitmap(bitmap);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    bitmap = (Bitmap) bundle.get("data");
                    productImage.setImageBitmap(bitmap);

                }
                break;
        }
    }


    private boolean checkCameraPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            boolean res1 = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED;
            if(res1){
                ActivityCompat.requestPermissions(Identification.this, new String[]{android.Manifest.permission.CAMERA} , 20);
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
            Toast.makeText(Identification.this , "Permission not Granted" , Toast.LENGTH_SHORT).show();
    }

    public String getFileExtention (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}



