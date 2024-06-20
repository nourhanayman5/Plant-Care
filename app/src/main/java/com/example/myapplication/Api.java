package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Api extends AppCompatActivity {

    private static final String TAG = Api.class.getSimpleName();
    private TextView textView;



    //    private static final int PICK_IMAGE_REQUEST = 1;
//    private ImageView imageView;
//    private Uri imageUri;
//    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        String plantName = getIntent().getStringExtra("diseasecause");
//        String diseaseName = getIntent().getStringExtra("disease_name");
//        String diseaseDescription = getIntent().getStringExtra("disease_description");
//        String diseaseTreatment = getIntent().getStringExtra("disease_treatment");
//
//        // Display the data
        TextView plantNameTextView = findViewById(R.id.plant_name);
//        TextView diseaseNameTextView = findViewById(R.id.disease_name);
//        TextView diseaseDescriptionTextView = findViewById(R.id.disease_description);
//        TextView diseaseTreatmentTextView = findViewById(R.id.disease_treatment);
//
        plantNameTextView.setText(plantName);
//        diseaseNameTextView.setText(diseaseName);
//        diseaseDescriptionTextView.setText(diseaseDescription);
//        diseaseTreatmentTextView.setText(diseaseTreatment);

//        imageView = findViewById(R.id.imageView);
//        apiService = RetofitClient.getRetrofitInstance().create(ApiService.class);
//
//        imageView.setOnClickListener(v -> openFileChooser());
    }

//    private void openFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//                && data != null && data.getData() != null) {
//            imageUri = data.getData();
//            try {
//                imageView.setImageURI(imageUri);
//                uploadImage();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void uploadImage() {
//        if (imageUri != null) {
//            File file = new File(getPathFromUri(imageUri));
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
//
//            Call<ImageAnalysisResponse> call = apiService.analyzeImage(imagePart);
//            call.enqueue(new Callback<ImageAnalysisResponse>() {
//                @Override
//                public void onResponse(Call<ImageAnalysisResponse> call, Response<ImageAnalysisResponse> response) {
//                    if (response.isSuccessful() && response.body() != null) {
//                        ImageAnalysisResponse analysisResponse = response.body();
//                        String imageUrl = analysisResponse.getImageUrl();
//                        String details = analysisResponse.getDetails();
//
//                        // Update UI with retrieved information on the main thread
//                        runOnUiThread(() -> {
//                            TextView textImageUrl = findViewById(R.id.textImageUrl);
//                            TextView textDetails = findViewById(R.id.textDetails);
//                            textImageUrl.setText("Image URL: " + imageUrl);
//                            textDetails.setText("Analysis Details: " + details);
//                        });
//                    } else {
//                        // Handle error
//                    }
//                }
//
//
//
//                @Override
//                public void onFailure(Call<ImageAnalysisResponse> call, Throwable t) {
//                    // Handle failure
//                }
//            });
//        }
//    }
//
//    private String getPathFromUri(Uri uri) {
//        String path = null;
//        String[] projection = { MediaStore.Images.Media.DATA };
//        try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
//            if (cursor != null && cursor.moveToFirst()) {
//                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                path = cursor.getString(columnIndex);
//            }
//        }
//        return path;
//    }
}