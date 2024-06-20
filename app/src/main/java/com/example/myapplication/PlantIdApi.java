package com.example.myapplication;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Header;


public interface PlantIdApi {
    @Multipart
    @POST("v2/identify")
    Call<PlantIdResponse> identifyPlant(
            @Header("Api-Key") String apiKey,
            @Part MultipartBody.Part image,
            @Part("organs") RequestBody organs,
            @Part("include_related_images") RequestBody includeRelatedImages,
            @Part("include_probability") RequestBody includeProbability
    );

}
