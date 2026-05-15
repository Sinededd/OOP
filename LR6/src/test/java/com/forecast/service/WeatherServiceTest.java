package com.forecast.service;

import com.forecast.client.WeatherDataClient;
import com.forecast.model.CurrentWeather;
import com.forecast.model.WeatherForecast;
import com.forecast.model.WeatherProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherDataClient client;

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        when(client.getProviderType()).thenReturn(WeatherProvider.OPEN_WEATHER);
        weatherService = new WeatherService(List.of(client));
    }

    @Test
    void getCurrentWeather_ReturnsCurrentWeatherSuccessfully() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");
        BigDecimal expectedTemp = new BigDecimal("15.5");
        WeatherProvider provider = WeatherProvider.OPEN_WEATHER;

        when(client.getCurrentTemperature(lat, lon)).thenReturn(expectedTemp);


        CurrentWeather result = weatherService.getCurrentWeather(lat, lon, provider);
        
        assertNotNull(result);
        assertEquals(expectedTemp, result.getTemperature());
    }


    @Test
    void shouldReturnCurrentWeatherForGivenProvider() {
        BigDecimal expectedTemp = new BigDecimal("18.5");
        when(client.getCurrentTemperature(any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(expectedTemp);

        CurrentWeather result = weatherService.getCurrentWeather(
                new BigDecimal("53.9006"),
                new BigDecimal("27.5590"),
                WeatherProvider.OPEN_WEATHER
        );

        assertNotNull(result);
        assertEquals(expectedTemp, result.getTemperature());
    }

    @Test
    void shouldDelegateForecastToCorrectClient() {
        List<WeatherForecast> expectedForecast = List.of(
                new WeatherForecast(LocalDate.of(2026, 5, 15), new BigDecimal("20.0")),
                new WeatherForecast(LocalDate.of(2026, 5, 16), new BigDecimal("22.5"))
        );

        when(client.getForecast(any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(expectedForecast);

        List<WeatherForecast> result = weatherService.getForecast(
                new BigDecimal("53.9006"),
                new BigDecimal("27.5590"),
                WeatherProvider.OPEN_WEATHER
        );

        assertEquals(2, result.size());
        assertEquals(new BigDecimal("20.0"), result.get(0).getTemperature());
    }

    @Test
    void shouldThrowException_WhenProviderNotFound() {
        assertThrows(NullPointerException.class, () ->
                weatherService.getCurrentWeather(
                        new BigDecimal("53.9"),
                        new BigDecimal("27.5"),
                        WeatherProvider.GOOGLE_WEATHER
                )
        );
    }

    @Test
    void getMultipleTemperatures_ReturnsMapOfCities() {
        List<String> cities = List.of("Минск", "London");
        when(client.getCurrentTemperature(any(), any()))
                .thenReturn(new BigDecimal("10.0"))
                .thenReturn(new BigDecimal("20.0"));

        var result = weatherService.getMultipleTemperatures(cities, WeatherProvider.OPEN_WEATHER);

        assertEquals(2, result.size());
        assertEquals(new BigDecimal("10.0"), result.get("Минск"));
        assertEquals(new BigDecimal("20.0"), result.get("London"));
    }
}