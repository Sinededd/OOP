package com.forecast.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WeatherForecast {
    private LocalDate date;
    private BigDecimal temperature;
}