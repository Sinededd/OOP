package com.forecast.service;

import com.forecast.client.WeatherDataClient;
import com.forecast.model.CurrentWeather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherDataClient client;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void getCurrentWeather_ReturnsCurrentWeatherSuccessfully() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");
        BigDecimal expectedTemp = new BigDecimal("15.5");

        when(client.getCurrentTemperature(lat, lon)).thenReturn(expectedTemp);

        CurrentWeather result = weatherService.getCurrentWeather(lat, lon);
        
        assertNotNull(result);
        assertEquals(expectedTemp, result.getTemperature());
    }
}