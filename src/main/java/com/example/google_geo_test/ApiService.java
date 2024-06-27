package com.example.google_geo_test;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("v1/geolocate")
    Call<LocationResponseModel> requestLocation(
            @Query("key") String apiKey,
            @Body PostRequestBody body

    );
}
