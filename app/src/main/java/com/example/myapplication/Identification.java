//package com.example.myapplication;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.core.content.FileProvider;
//
//import android.annotation.SuppressLint;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.myapplication.ml.ConvertedModel1;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import org.tensorflow.lite.DataType;
//import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class Identification extends AppCompatActivity {
//
//    private static final int CAMERA_REQUEST_CODE = 1;
//    private static final int GALLERY_REQUEST_CODE = 2;
//    private static final int PERMISSIONS_REQUEST_CODE = 100;
//
//    private static final int REQUEST_CAPTURE_IMAGE = 1001;
//    private File PhotoFile;
//
//    private Uri imageUri;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_identification);
//
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//
//
//        ImageView cameraButton = findViewById(R.id.camera);
//        ImageView galleryButton = findViewById(R.id.gallery);
//
//        // Check permissions and capture an image from the camera
//        cameraButton.setOnClickListener(v -> {
//            if (checkPermissions()) {
//                captureImageFromCamera();
//            }
//        });
//
//        // Check permissions and select an image from the gallery
//        galleryButton.setOnClickListener(v -> {
//            if (checkPermissions()) {
//                selectImageFromGallery();
//            }
//        });
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
//        bottomNavigationView.setSelectedItemId(R.id.bottom_cam);
//
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.bottom_home) {
//                Intent i1 = new Intent(Identification.this,Home.class);
//                startActivity(i1);
//                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
//                finish();
//                return true;
//            } else if (itemId == R.id.bottom_cam) {
//
//                return true;
//            } else if (itemId == R.id.bottom_fav) {
//                Intent i2 = new Intent(Identification.this,Favorite.class);
//                startActivity(i2);
//                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
//                finish();
//                return true;
//            } else if (itemId == R.id.bottom_profile) {
//                Intent i3 = new Intent(Identification.this,Profile.class);
//                startActivity(i3);
//                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
//                finish();
//                return true;
//            }
//            return false;
//        });
//    }
//
//    private boolean checkPermissions() {
//
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            // Request permissions
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE},
//                    PERMISSIONS_REQUEST_CODE);
//            return false;
//        }
//        return true;
//    }
//
//    // Capture an image from the camera
//    private void captureImageFromCamera() {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
//        }
//    }
//
//
//    // Select an image from the gallery
//    private void selectImageFromGallery() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
//        }
//    }
//
//    // Handle results from camera or gallery
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            if (requestCode == CAMERA_REQUEST_CODE) {
//                // Handle the camera result
//                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
//                // Convert the Bitmap to a URI and pass it to the display activity
//                imageUri = getImageUriFromBitmap(imageBitmap);
//                launchDisplayActivity(imageUri);
//            } else if (requestCode == GALLERY_REQUEST_CODE) {
//                // Handle the gallery result
//                imageUri = data.getData();
//                launchDisplayActivity(imageUri);
//            } else if (requestCode == REQUEST_CAPTURE_IMAGE) {
//                Bitmap bitmap = BitmapFactory.decodeFile(PhotoFile.getAbsolutePath());
//                imageUri = getImageUriFromBitmap(bitmap);
//                launchDisplayActivity(imageUri);
//            }
//        }
//    }
//
//    // Convert Bitmap to URI
//
//    private Uri getImageUriFromBitmap(Bitmap bitmap) {
//        // Define the directory where the image will be saved
//        File imagesDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "camera_images");
//        if (!imagesDir.exists()) {
//            imagesDir.mkdirs(); // Create the directory if it doesn't exist
//        }
//
//        // Create a unique filename for the image
//        String imageName = "IMG_" + System.currentTimeMillis() + ".jpg";
//
//        // Create a file in the specified directory
//        File imageFile = new File(imagesDir, imageName);
//
//        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
//            // Compress the bitmap and save it to the file
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null; // Return null if there is an error
//        }
//
//        // Create a URI from the file using FileProvider (optional, recommended for Android N and above)
//        return FileProvider.getUriForFile(
//                this,
//                "com.example.myapplication.provider",
//                imageFile
//        );
//    }
//
//    private void launchDisplayActivity(Uri imageUri) {
//        Intent intent = new Intent(this, ImageDisplayActivity.class);
//        intent.putExtra("imageUri", imageUri.toString());
//        startActivity(intent);
//    }
//}
//
//
//


package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Identification extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private File PhotoFile;
    private Uri imageUri;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageView cameraButton = findViewById(R.id.camera);
        ImageView galleryButton = findViewById(R.id.gallery);

        cameraButton.setOnClickListener(v -> {
            if (checkPermissions()) {
                captureImageFromCamera();
            }
        });

        galleryButton.setOnClickListener(v -> {
            if (checkPermissions()) {
                selectImageFromGallery();
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

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private void captureImageFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            try {
                PhotoFile = createImageFile();
                if (PhotoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.myapplication.provider",
                            PhotoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void selectImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                if (PhotoFile != null) {
                    Uri photoURI = Uri.fromFile(PhotoFile);
                    launchDisplayActivity(photoURI);
                }
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                imageUri = data.getData();
                launchDisplayActivity(imageUri);
            }
        }
    }

    private Uri getImageUriFromBitmap(Bitmap bitmap) {
        File imagesDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "camera_images");
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }

        String imageName = "IMG_" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(imagesDir, imageName);

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return FileProvider.getUriForFile(
                this,
                "com.example.myapplication.provider",
                imageFile
        );
    }

    private void launchDisplayActivity(Uri imageUri) {
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        intent.putExtra("imageUri", imageUri.toString());
        startActivity(intent);
    }
}
