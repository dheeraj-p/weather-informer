package com.bca.weatherinformer.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class WeatherResponse {
    private String cityName;
    private String countryCode;
    private List<DayForecast> data;
}

