package com.forecast.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenWeatherClientTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestClient restClient;

    @InjectMocks
    private OpenWeatherClient openWeatherClient;

    @Test
    void getCurrentTemperature_ReturnsTemperature_WhenResponseIsValid() {
        // Arrange
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");
        String jsonResponse = "{\"main\":{\"temp\":18.5}}";

        when(restClient.get()
                .uri(any(Function.class))
                .retrieve()
                .toEntity(String.class))
                .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));

        BigDecimal result = openWeatherClient.getCurrentTemperature(lat, lon);

        assertEquals(new BigDecimal("18.5"), result);
    }

    @Test
    void getCurrentTemperature_ThrowsException_WhenStatusIsNotOk() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");

        when(restClient.get()
                .uri(any(Function.class))
                .retrieve()
                .toEntity(String.class))
                .thenReturn(new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> openWeatherClient.getCurrentTemperature(lat, lon));

        assertEquals("openweather returned bad status: 401 UNAUTHORIZED", exception.getMessage());
    }

    @Test
    void getCurrentTemperature_ThrowsException_WhenTemperatureNodeIsMissing() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");

        String jsonResponse = "{\"weather\":[{\"description\":\"clear sky\"}]}";

        when(restClient.get()
                .uri(any(Function.class))
                .retrieve()
                .toEntity(String.class))
                .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> openWeatherClient.getCurrentTemperature(lat, lon));

        assertEquals("failed to decode response: missing temperature data", exception.getMessage());
    }

    @Test
    void getCurrentTemperature_ThrowsException_WhenJsonIsInvalid() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");
        String invalidJsonResponse = "{ broken_json: ";

        when(restClient.get()
                .uri(any(Function.class))
                .retrieve()
                .toEntity(String.class))
                .thenReturn(new ResponseEntity<>(invalidJsonResponse, HttpStatus.OK));

        assertThrows(RuntimeException.class,
                () -> openWeatherClient.getCurrentTemperature(lat, lon));
    }
}