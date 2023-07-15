package com.bca.weatherinformer.services;

import com.bca.weatherinformer.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("v2.0/forecast/daily")
    Call<WeatherResponse> fetchWeather(@Query("lat") String latitude,
                                       @Query("lon") String longitude,
                                       @Query("key") String apiKey);
}
