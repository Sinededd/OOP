package com.forecast.client;

import com.forecast.model.WeatherForecast;
import com.forecast.model.WeatherProvider;
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
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenWeatherClientTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestClient restClient;

    @InjectMocks
    private OpenWeatherClient openWeatherClient;

    

    @Test
    void getProviderType_ReturnsOpenWeather() {
        assertEquals(WeatherProvider.OPEN_WEATHER, openWeatherClient.getProviderType());
    }

    

    @Test
    void getCurrentTemperature_ReturnsTemperature_WhenResponseIsValid() {
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

        assertEquals("OpenWeather returned bad status: 401 UNAUTHORIZED", exception.getMessage());
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

        assertEquals("Missing temperature data in OpenWeather response", exception.getMessage());
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

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> openWeatherClient.getCurrentTemperature(lat, lon));

        assertTrue(exception.getMessage().contains("Failed to parse OpenWeather current weather response"));
    }

    

    @Test
    void getForecast_ReturnsList_WhenResponseIsValid() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");

        String jsonResponse = """
                {
                  "list": [
                    {"dt": 1715760000, "main": {"temp": 15.0}},
                    {"dt": 1715792400, "main": {"temp": 16.0}},
                    {"dt": 1715824800, "main": {"temp": 17.0}},
                    {"dt": 1715857200, "main": {"temp": 18.0}},
                    {"dt": 1715889600, "main": {"temp": 19.0}},
                    {"dt": 1715922000, "main": {"temp": 20.0}},
                    {"dt": 1715954400, "main": {"temp": 21.0}},
                    {"dt": 1715986800, "main": {"temp": 22.0}},
                    {"dt": 1716019200, "main": {"temp": 23.0}}
                  ]
                }
                """;

        when(restClient.get()
                .uri(any(Function.class))
                .retrieve()
                .toEntity(String.class))
                .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));

        List<WeatherForecast> result = openWeatherClient.getForecast(lat, lon);

        assertEquals(2, result.size());           
        assertEquals(new BigDecimal("15.0"), result.get(0).getTemperature());
        assertEquals(new BigDecimal("23.0"), result.get(1).getTemperature());
        assertEquals(LocalDate.of(2024, 5, 15), result.get(0).getDate()); 
    }

    @Test
    void getForecast_ThrowsException_WhenStatusIsNotOk() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");

        when(restClient.get()
                .uri(any(Function.class))
                .retrieve()
                .toEntity(String.class))
                .thenReturn(new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> openWeatherClient.getForecast(lat, lon));

        assertEquals("OpenWeather returned bad status: 403 FORBIDDEN", exception.getMessage());
    }

    @Test
    void getForecast_ThrowsException_WhenJsonIsInvalid() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");

        when(restClient.get()
                .uri(any(Function.class))
                .retrieve()
                .toEntity(String.class))
                .thenReturn(new ResponseEntity<>("{ invalid json", HttpStatus.OK));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> openWeatherClient.getForecast(lat, lon));

        assertTrue(exception.getMessage().contains("Failed to parse OpenWeather forecast response"));
    }
}