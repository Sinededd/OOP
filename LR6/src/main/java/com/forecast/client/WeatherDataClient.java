package com.forecast.client;

import com.forecast.model.WeatherForecast;
import com.forecast.model.WeatherProvider;

import java.math.BigDecimal;
import java.util.List;

public interface WeatherDataClient {
    WeatherProvider getProviderType();
    BigDecimal getCurrentTemperature(BigDecimal lat, BigDecimal lon);
    List<WeatherForecast> getForecast(BigDecimal lat, BigDecimal lon);
}
