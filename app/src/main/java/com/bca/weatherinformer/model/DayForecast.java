package com.bca.weatherinformer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DayForecast {
    String validDate;
    double maxTemp;
    double minTemp;
    int pop;
    double windSpd;
    double temp;
    double pres;
    DayForecast.Weather weather;

    @AllArgsConstructor
    @Builder
    @Data
    public static class Weather {
        String icon;
        String code;
        String description;
    }
}
