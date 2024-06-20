//package com.example.myapplication;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.myapplication.ml.ConvertedModel1;
//
//import org.tensorflow.lite.DataType;
//import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
//
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//
//public class ImageDisplayActivity extends AppCompatActivity {
//
//    private static final String TAG = "ImageDisplayActivity";
//    private final String[] classNames = {
//            "Tomato Bacterial spot", "Tomato mosaic virus", "Tomato Spider mites Two spotted spider mite",
//            "Pepperbell Bacterial spot", "Potato Late blight", "Pepperbell healthy", "Potato Early blight",
//            "Tomato Late blight", "Tomato YellowLeaf Curl Virus", "Tomato Target Spot",
//            "Tomato Early blight", "Potato healthy", "Tomato Septoria leaf spot", "Tomato healthy", "Tomato Leaf Mold"
//    };
//
//    Bitmap bitmap = null;
//    String plantName;
//    TextView resultView;
//    ImageView favoriteIcon;
//    FavDB favDB;
//    User user;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image_display);
//
//        ImageView imageView = findViewById(R.id.imageView);
//        resultView = findViewById(R.id.idResult);
//        favoriteIcon = findViewById(R.id.favIcon);
//        favDB = new FavDB(getApplicationContext());
//        user = (User) getApplicationContext();
//        String username = user != null ? user.getEmail() : "";
//
//        // Retrieve the image URI or bitmap from the Intent extras
//        if (getIntent().hasExtra("imageUri")) {
//            Uri imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
//            imageView.setImageURI(imageUri);
//            bitmap = getBitmapFromUri(imageUri);
//            processImage(bitmap);
//        } else if (getIntent().hasExtra("bitmap")) {
//            bitmap = getIntent().getParcelableExtra("bitmap");
//            imageView.setImageBitmap(bitmap);
//            processImage(bitmap);
//        } else {
//            Log.e(TAG, "No image data found in Intent extras");
//            resultView.setText("Error: No image data found");
//        }
//
//        favoriteIcon.setOnClickListener(v -> {
//            if (bitmap != null) {
//                favDB.insertFav(plantName, username, bitmap);
//                changeIconOnClick();
//            } else {
//                Log.e(TAG, "Bitmap is null, cannot add to favorites");
//            }
//        });
//    }
//
//    private void processImage(Bitmap bitmap) {
//        if (bitmap != null) {
//            try {
//                // Create an instance of the model
//                ConvertedModel1 model = ConvertedModel1.newInstance(this);
//
//                // Resize bitmap for model
//                bitmap = Bitmap.createScaledBitmap(bitmap, 64, 64, false);
//
//                // Prepare input feature for the model
//                TensorBuffer inputFeature0 = prepareInputFeature(bitmap);
//
//                // Process the input feature using the model
//                ConvertedModel1.Outputs outputs = model.process(inputFeature0);
//                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
//
//                // Get the class name from the output
//                int maxIndex = getMax(outputFeature0.getFloatArray());
//                plantName = classNames[maxIndex];
//                resultView.setText(classNames[maxIndex]);
//
//                // Close the model
//                model.close();
//            } catch (IOException e) {
//                Log.e(TAG, "Error loading TensorFlow Lite model: " + e.getMessage());
//                resultView.setText("Error: Model loading failed");
//            } catch (Exception e) {
//                Log.e(TAG, "Error processing image: " + e.getMessage());
//                resultView.setText("Error: Image processing failed");
//            }
//        } else {
//            Log.e(TAG, "Bitmap is null");
//            resultView.setText("Error: Bitmap is null");
//        }
//    }
//
//    private TensorBuffer prepareInputFeature(Bitmap bitmap) {
//        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 64, 64, 3}, DataType.FLOAT32);
//
//        // Create a byte buffer for input data
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 64 * 64 * 3);
//        byteBuffer.order(ByteOrder.nativeOrder());
//
//        int[] intValues = new int[64 * 64];
//        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
//
//        int pixelIndex = 0;
//        for (int i = 0; i < 64; i++) {
//            for (int j = 0; j < 64; j++) {
//                int pixelValue = intValues[pixelIndex++];
//                byteBuffer.putFloat(((pixelValue >> 16) & 0xFF) * (1.f / 255));
//                byteBuffer.putFloat(((pixelValue >> 8) & 0xFF) * (1.f / 255));
//                byteBuffer.putFloat((pixelValue & 0xFF) * (1.f / 255));
//            }
//        }
//
//        // Load the byte buffer into the input feature
//        inputFeature0.loadBuffer(byteBuffer);
//        return inputFeature0;
//    }
//
//    private Bitmap getBitmapFromUri(Uri uri) {
//        try {
//            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//        } catch (IOException e) {
//            Log.e(TAG, "Error loading bitmap from URI: " + e.getMessage());
//            return null;
//        }
//    }
//
//    private int getMax(float[] array) {
//        int maxIndex = 0;
//        float maxValue = Float.MIN_VALUE;
//        for (int i = 0; i < array.length; i++) {
//            if (array[i] > maxValue) {
//                maxValue = array[i];
//                maxIndex = i;
//            }
//        }
//        return maxIndex;
//    }
//
//    public void changeIconOnClick() {
//        if (favoriteIcon != null) {
//            Drawable currentIcon = favoriteIcon.getDrawable();
//            if (currentIcon != null && currentIcon.getConstantState() != null) {
//                Drawable favoriteBorderIcon = getResources().getDrawable(R.drawable.baseline_favorite_border_24);
//                Drawable favoriteIconDrawable = getResources().getDrawable(R.drawable.baseline_favorite2_24);
//
//                // Check the current icon and change it accordingly
//                if (currentIcon.getConstantState() == favoriteBorderIcon.getConstantState()) {
//                    favoriteIcon.setImageResource(R.drawable.baseline_favorite2_24);
//                } else {
//                    favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24);
//                }
//            } else {
//                Log.e(TAG, "Current icon or its constant state is null");
//            }
//        } else {
//            Log.e(TAG, "Favorite icon view is null");
//        }
//    }
//}

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ml.ConvertedModel1;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ImageDisplayActivity extends AppCompatActivity {

//    private static final String TAG = "ImageDisplayActivity";
    private static final String BASE_URL = "https://api.plant.id/";
    private static final String API_KEY = "FDfTBYV31NJDTa4obYNUI66aXCLxaGgKMa1uMFPQb2lwVeNXXN";
    private static final String TAG = ImageDisplayActivity.class.getSimpleName();

    private final String[] classNames = {
            "Tomato Bacterial spot", "Tomato mosaic virus", "Tomato Spider mites Two spotted spider mite",
            "Pepperbell Bacterial spot", "Potato Late blight", "Pepperbell healthy", "Potato Early blight",
            "Tomato Late blight", "Tomato YellowLeaf Curl Virus", "Tomato Target Spot",
            "Tomato Early blight", "Potato healthy", "Tomato Septoria leaf spot", "Tomato healthy", "Tomato Leaf Mold"
    };

    private Bitmap bitmap = null;
    private String plantName = "";
    private TextView resultView, description;
    private ImageView imageView;
    private ImageView favoriteIcon;
    private FavDB favDB;
    private User user;
    Button Api_Btn,plantTreatment;
    String diseaseDesc , diseaseCause , treatment ;
    Uri imageUri;
//    String extract = "";
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        imageView = findViewById(R.id.imageView);
        resultView = findViewById(R.id.idResult);
        favoriteIcon = findViewById(R.id.favIcon);
        description = (TextView) findViewById(R.id.diseasedesc1);
        favDB = new FavDB(getApplicationContext());
        user = (User) getApplicationContext();
        Api_Btn = (Button) findViewById(R.id.api_btn);
        plantTreatment = (Button) findViewById(R.id.diseaseTreament);
        String username = user != null ? user.getEmail() : "";

        // Set OnClickListener for favoriteIcon
        favoriteIcon.setOnClickListener(v -> {
            if (bitmap != null) {
                favDB.insertFav(plantName, username, bitmap);
                changeIconOnClick();
            } else {
                Log.e(TAG, "Bitmap is null, cannot add to favorites");
            }
        });

        // Set initial image if available
        if (getIntent().hasExtra("imageUri")) {
            imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
            setImageAndProcess(imageUri);
        } else if (getIntent().hasExtra("bitmap")) {
            bitmap = getIntent().getParcelableExtra("bitmap");
            imageView.setImageBitmap(bitmap);
            processImage(bitmap);

        } else {
            Log.e(TAG, "No image data found in Intent extras");
            resultView.setText("Error: No image data found");
        }

//        try {
//            loadJson(plantName);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        description.setText(diseaseDesc);
        new FetchDataTask().execute("https://plantapp-70a2a-default-rtdb.firebaseio.com/"+ plantName +".json");

        Api_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent disease = new Intent(ImageDisplayActivity.this,Api.class);
                disease.putExtra("diseasecause",diseaseCause);
                startActivity(disease);
            }
        });

        plantTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent treat = new Intent(ImageDisplayActivity.this,Treatment.class);
                treat.putExtra("treatment",treatment);
                startActivity(treat);
            }
        });

    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return NetworkUtils.fetchData(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Plants>>() {}.getType();
            List<Plants> blightList = gson.fromJson(result, listType);

            if (blightList != null && !blightList.isEmpty()) {
                Plants blight = blightList.get(0);
                description.setText(blight.getDescription());
                diseaseCause = blight.getDiseaseCause();
                treatment = blight.getTreatment();

            }
        }
    }


//
//    private void uploadImage(Uri imageUri) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        PlantIdApi api = retrofit.create(PlantIdApi.class);
//
//        // Get the file path from the URI
//        String filePath = getRealPathFromURI(imageUri);
//        File file = new File(filePath);
//
//        // Create request body for the file
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("images", file.getName(), requestFile);
//
//        // Add other request parameters
//        RequestBody organs = RequestBody.create(MediaType.parse("text/plain"), "leaf,flower");
//        RequestBody includeRelatedImages = RequestBody.create(MediaType.parse("text/plain"), "true");
//        RequestBody includeProbability = RequestBody.create(MediaType.parse("text/plain"), "true");
//
//        // Make the API call
//        Call<PlantIdResponse> call = api.identifyPlant(API_KEY, body, organs, includeRelatedImages, includeProbability);
//        call.enqueue(new Callback<PlantIdResponse>() {
//
//            @Override
//            public void onResponse(Call<PlantIdResponse> call, Response<PlantIdResponse> response) {
//                String plant_Name;
//                String disease_Name;
//                String diseaseDescription;
//                String diseaseTreatment;
//                if (response.isSuccessful() && response.body() != null) {
//                    PlantIdResponse plantIdResponse = response.body();
//
//                    // Check if suggestions list is not null and not empty
//                    if (plantIdResponse.suggestions != null && !plantIdResponse.suggestions.isEmpty()) {
//                        plant_Name = plantIdResponse.suggestions.get(0).plantName;
//                        // Access other properties of the suggestion as needed
//                    } else {
//                        Log.e("PlantID", "No suggestions found");
//                        return; // Exit early to avoid further processing
//                    }
//
//                    // Check if diseases list is not null and not empty
////                    if (plantIdResponse.diseases != null && !plantIdResponse.diseases.isEmpty()) {
////                        disease_Name = plantIdResponse.diseases.get(0).diseaseName;
////                        diseaseDescription = plantIdResponse.diseases.get(0).description;
////                        diseaseTreatment = plantIdResponse.diseases.get(0).treatment;
////
////                        // Access other properties of the disease as needed
////                    } else {
////                        Log.e("PlantID", "No diseases found");
////                        return; // Exit early to avoid further processing
////                    }
//
//                    // Start PlantDetailsActivity and pass the data
//                    Intent intent = new Intent(ImageDisplayActivity.this, Api.class);
//                    intent.putExtra("plant_name", plant_Name);
////                    intent.putExtra("disease_name", disease_Name);
////                    intent.putExtra("disease_description", diseaseDescription);
////                    intent.putExtra("disease_treatment", diseaseTreatment);
//
//                    // Add other extras as needed
//                    startActivity(intent);
//                } else {
//                    Log.e("PlantID", "Request failed: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PlantIdResponse> call, Throwable t) {
//                Log.e("PlantID", "Network error: " + t.getMessage());
//            }
//        });
//    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    private void setImageAndProcess(Uri imageUri) {
        imageView.setImageURI(imageUri);
        bitmap = getBitmapFromUri(imageUri);
        processImage(bitmap);
    }

    private void processImage(Bitmap bitmap) {
        if (bitmap != null) {
            try {

                ConvertedModel1 model = ConvertedModel1.newInstance(ImageDisplayActivity.this);

                // Creates inputs for reference.
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 64, 64, 3}, DataType.FLOAT32);
                bitmap=Bitmap.createScaledBitmap(bitmap,64,64,false);

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
                plantName=classNames[getMax(outputFeature0.getFloatArray())];
                resultView.setText(classNames[getMax(outputFeature0.getFloatArray())]+" ");

                // Releases model resources if no longer used.
                model.close();
            } catch (IOException e) {
                Log.e(TAG, "Error loading TensorFlow Lite model: " + e.getMessage());
                resultView.setText("Error: Model loading failed");
            } catch (Exception e) {
                Log.e(TAG, "Error processing image: " + e.getMessage());
                resultView.setText("Error: Image processing failed");
            }
        } else {
            Log.e(TAG, "Bitmap is null");
            resultView.setText("Error: Bitmap is null");
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            Log.e(TAG, "Error loading bitmap from URI: " + e.getMessage());
            return null;
        }
    }

    private int getMax(float[] array) {
        int maxIndex = 0;
        float maxValue = Float.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public void changeIconOnClick() {
        if (favoriteIcon != null) {
            Drawable currentIcon = favoriteIcon.getDrawable();
            if (currentIcon != null && currentIcon.getConstantState() != null) {
                Drawable favoriteBorderIcon = getResources().getDrawable(R.drawable.baseline_favorite_border_24);
                Drawable favoriteIconDrawable = getResources().getDrawable(R.drawable.baseline_favorite2_24);

                // Check the current icon and change it accordingly
                if (currentIcon.getConstantState().equals(favoriteBorderIcon.getConstantState())) {
                    favoriteIcon.setImageResource(R.drawable.baseline_favorite2_24);
                } else {
                    favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24);
                }
            } else {
                Log.e(TAG, "Current icon or its constant state is null");
            }
        } else {
            Log.e(TAG, "Favorite icon view is null");
        }
    }

    //    private Bitmap preprocessImage(Bitmap image) {
//        // Resize the image
//        Bitmap resizedImage = Bitmap.createScaledBitmap(image, 64, 64, false);
//
//        // Apply median filtering to remove noise (You can implement your own filtering algorithm)
//        // For simplicity, we're skipping this step in this example
//
//        // Convert the image to grayscale
////        Bitmap grayscaleImage = toGrayscale(resizedImage);
////        Bitmap medianfilter = medianFilter(resizedImage);
//        // Normalize pixel values to the range [0, 1]
//        Bitmap normalizedImage = normalize(resizedImage);
//
//        Bitmap medianfilter = medianFilter(normalizedImage);
//        return medianfilter;
//
//    }
//
//
//
//    private Bitmap normalize(Bitmap image) {
//        Bitmap normalizedImage = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
//
//        // Iterate through each pixel in the image
//        for (int x = 0; x < image.getWidth(); ++x) {
//            for (int y = 0; y < image.getHeight(); ++y) {
//                // Get the pixel color
//                int pixel = image.getPixel(x, y);
//
//                // Extract red, green, and blue components
//                int red = Color.red(pixel);
//                int green = Color.green(pixel);
//                int blue = Color.blue(pixel);
//
//                // Normalize each color component to [0, 1] range
//                float normalizedRed = red / 255.0f;
//                float normalizedGreen = green / 255.0f;
//                float normalizedBlue = blue / 255.0f;
//
//                // Convert the normalized values back to integers in the range [0, 255]
//                int newRed = Math.round(normalizedRed * 255);
//                int newGreen = Math.round(normalizedGreen * 255);
//                int newBlue = Math.round(normalizedBlue * 255);
//
//                // Combine normalized components back into a color
//                int newColor = Color.rgb(newRed, newGreen, newBlue);
//
//                // Set the new color in the normalized image
//                normalizedImage.setPixel(x, y, newColor);
//            }
//        }
//
//        return normalizedImage;
//    }
//
//    private Bitmap medianFilter(Bitmap image) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//        Bitmap filteredImage = Bitmap.createBitmap(width, height, image.getConfig());
//
//        // Define the kernel size (3x3 in this case)
//        int kernelSize = 3;
//        int halfKernelSize = kernelSize / 2;
//
//        // Create a list to hold pixel values for computing the median
//        int[] pixelValues = new int[kernelSize * kernelSize];
//
//        // Iterate through the image
//        for (int x = 0; x < width; ++x) {
//            for (int y = 0; y < height; ++y) {
//                int pixelCount = 0;
//
//                // Collect pixels in the kernel region around the current pixel
//                for (int dx = -halfKernelSize; dx <= halfKernelSize; ++dx) {
//                    for (int dy = -halfKernelSize; dy <= halfKernelSize; ++dy) {
//                        int nx = x + dx;
//                        int ny = y + dy;
//
//                        // Check if the coordinates are within the image bounds
//                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
//                            // Get the pixel value and add it to the list
//                            int pixel = image.getPixel(nx, ny);
//                            pixelValues[pixelCount] = Color.red(pixel);
//                            pixelCount++;
//                        }
//                    }
//                }
//
//                // Sort the pixel values and find the median
//                Arrays.sort(pixelValues, 0, pixelCount);
//                int medianValue = pixelValues[pixelCount / 2];
//
//                // Set the median value as the new pixel value in the filtered image
//                int newColor = Color.rgb(medianValue, medianValue, medianValue);
//                filteredImage.setPixel(x, y, newColor);
//            }
//        }
//
//        return filteredImage;
//    }
}

