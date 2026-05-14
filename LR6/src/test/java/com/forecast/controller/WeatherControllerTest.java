package com.forecast.controller;

import com.forecast.model.CurrentWeather;
import com.forecast.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void getCurrentWeather_Returns200_WhenValidCoordinates() throws Exception {
        CurrentWeather mockWeather = new CurrentWeather(new BigDecimal("22.5"));
        when(weatherService.getCurrentWeather(any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(mockWeather);

        mockMvc.perform(get("/api/v1/weather")
                        .param("lat", "53.9006")
                        .param("lon", "27.5590")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.temperature").value(22.5));
    }

    @Test
    void getCurrentWeather_Returns400_WhenInvalidCoordinates() throws Exception {
        mockMvc.perform(get("/api/v1/weather")
                        .param("lat", "invalid_lat")
                        .param("lon", "27.5590"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("invalid coordinates"));
    }

    @Test
    void getCurrentWeather_Returns500_WhenServiceThrowsException() throws Exception {
        when(weatherService.getCurrentWeather(any(), any()))
                .thenThrow(new RuntimeException("External API down"));

        mockMvc.perform(get("/api/v1/weather")
                        .param("lat", "53.9006")
                        .param("lon", "27.5590"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("External API down"));
    }
}