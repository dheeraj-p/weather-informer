package com.bca.weatherinformer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bca.weatherinformer.model.DayForecast;
import com.bca.weatherinformer.model.WeatherResponse;
import com.bca.weatherinformer.services.WeatherService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherInformationActivity extends AppCompatActivity {

    private double latitude;
    private double longitude;

    private TextView cityNameTV, currentTempTV, currentDescriptionTV, windSpeedTV, pressureTV, popTV;
    private TextView day1TitleTV, day1MinTempTV, day1MaxTempTV;
    private TextView day2TitleTV, day2MinTempTV, day2MaxTempTV;
    private TextView day3TitleTV, day3MinTempTV, day3MaxTempTV;
    private TextView day4TitleTV, day4MinTempTV, day4MaxTempTV;

    private ImageView currentWeatherIV, day1IconIV, day2IconIV, day3IconIV, day4IconIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_information);

        this.latitude = getIntent().getDoubleExtra("latitude", 0);
        this.longitude = getIntent().getDoubleExtra("longitude", 0);

        findViews();
        fetchWeatherData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void fetchWeatherData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherbit.io/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()))
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> weatherCall = service.fetchWeather(
                String.valueOf(this.latitude),
                String.valueOf(this.longitude),
                "ed65eeeacd1e4dcb8cf0eb06e8422b0a");

        weatherCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                WeatherResponse weatherResponse = response.body();
                renderWeatherInfo(weatherResponse);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(WeatherInformationActivity.this, "Failed to fetch weather data", Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    private void findViews() {
        cityNameTV = findViewById(R.id.cityNameTV);
        currentTempTV = findViewById(R.id.currentTempTV);
        currentDescriptionTV = findViewById(R.id.currentWDescriptionTV);
        windSpeedTV = findViewById(R.id.windSpeedTV);
        pressureTV = findViewById(R.id.pressureTV);
        popTV = findViewById(R.id.popTV);
        currentWeatherIV = findViewById(R.id.currentWeatherIV);

        day1TitleTV = findViewById(R.id.day1TitileTv);
        day1MaxTempTV = findViewById(R.id.day1MaxTempTV);
        day1MinTempTV = findViewById(R.id.day1MinTempTV);
        day1IconIV = findViewById(R.id.day1IconIV);

        day2TitleTV = findViewById(R.id.day2TitileTv);
        day2MaxTempTV = findViewById(R.id.day2MaxTempTV);
        day2MinTempTV = findViewById(R.id.day2MinTempTV);
        day2IconIV = findViewById(R.id.day2IconIV);

        day3TitleTV = findViewById(R.id.day3TitileTv);
        day3MaxTempTV = findViewById(R.id.day3MaxTempTV);
        day3MinTempTV = findViewById(R.id.day3MinTempTV);
        day3IconIV = findViewById(R.id.day3IconIV);

        day4TitleTV = findViewById(R.id.day4TitileTv);
        day4MaxTempTV = findViewById(R.id.day4MaxTempTV);
        day4MinTempTV = findViewById(R.id.day4MinTempTV);
        day4IconIV = findViewById(R.id.day4IconIV);
    }

    private String getDayTitle(String validDate) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate date = LocalDate.parse(validDate);
            return date.getDayOfWeek().toString().substring(0, 3);
        }
        return validDate.split("-")[0];
    }

    private String formatTemperature(double temperature) {
        return temperature + " °C";
    }

    private String formatWindSpeed(double windSpeed) {
        return windSpeed + " m/s (Wind Speed)";
    }

    private String formatPressure(double pressure) {
        return pressure + " mb (Pressure)";
    }

    private String formatPrecipitation(int pop) {
        return pop + "% (Precipitation)";
    }

    private String getIconUrl(String iconId) {
        return "https://cdn.weatherbit.io/static/img/icons/" + iconId + ".png";
    }
    private void renderWeatherInfo(WeatherResponse weatherResponse) {
        cityNameTV.setText(weatherResponse.getCityName());

        DayForecast currentDay = weatherResponse.getData().get(0);

        currentTempTV.setText(formatTemperature(currentDay.getTemp()));
        currentDescriptionTV.setText(currentDay.getWeather().getDescription());
        windSpeedTV.setText(formatWindSpeed(currentDay.getWindSpd()));
        pressureTV.setText(formatPressure(currentDay.getPres()));
        popTV.setText(formatPrecipitation(currentDay.getPop()));
        Picasso.get().load(getIconUrl(currentDay.getWeather().getIcon())).into(currentWeatherIV);

        DayForecast day1Forecast = weatherResponse.getData().get(1);
        day1TitleTV.setText(getDayTitle(day1Forecast.getValidDate()));
        day1MaxTempTV.setText(formatTemperature(day1Forecast.getMaxTemp()));
        day1MinTempTV.setText(formatTemperature(day1Forecast.getMinTemp()));
        Picasso.get().load(getIconUrl(day1Forecast.getWeather().getIcon())).into(day1IconIV);

        DayForecast day2Forecast = weatherResponse.getData().get(2);
        day2TitleTV.setText(getDayTitle(day2Forecast.getValidDate()));
        day2MaxTempTV.setText(formatTemperature(day2Forecast.getMaxTemp()));
        day2MinTempTV.setText(formatTemperature(day2Forecast.getMinTemp()));
        Picasso.get().load(getIconUrl(day2Forecast.getWeather().getIcon())).into(day2IconIV);

        DayForecast day3Forecast = weatherResponse.getData().get(3);
        day3TitleTV.setText(getDayTitle(day3Forecast.getValidDate()));
        day3MaxTempTV.setText(formatTemperature(day3Forecast.getMaxTemp()));
        day3MinTempTV.setText(formatTemperature(day3Forecast.getMinTemp()));
        Picasso.get().load(getIconUrl(day3Forecast.getWeather().getIcon())).into(day3IconIV);

        DayForecast day4Forecast = weatherResponse.getData().get(4);
        day4TitleTV.setText(getDayTitle(day4Forecast.getValidDate()));
        day4MaxTempTV.setText(formatTemperature(day4Forecast.getMaxTemp()));
        day4MinTempTV.setText(formatTemperature(day4Forecast.getMinTemp()));
        Picasso.get().load(getIconUrl(day4Forecast.getWeather().getIcon())).into(day4IconIV);
    }
}