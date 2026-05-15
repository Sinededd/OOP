package com.forecast.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forecast.model.WeatherForecast;
import com.forecast.model.WeatherProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenWeatherClient implements WeatherDataClient {

    @Qualifier("openWeatherRestClient")
    private final RestClient restClient;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public WeatherProvider getProviderType() {
        return WeatherProvider.OPEN_WEATHER;
    }

    @Override
    public BigDecimal getCurrentTemperature(BigDecimal lat, BigDecimal lon) {
        String responseBody = fetchWeatherData("/weather", lat, lon);

        try {
            JsonNode rootNode = mapper.readTree(responseBody);
            JsonNode mainNode = rootNode.get("main");

            if (mainNode == null || !mainNode.has("temp")) {
                throw new RuntimeException("Missing temperature data in OpenWeather response");
            }

            return new BigDecimal(mainNode.get("temp").asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse OpenWeather current weather response", e);
        }
    }

    @Override
    public List<WeatherForecast> getForecast(BigDecimal lat, BigDecimal lon) {
        String responseBody = fetchWeatherData("/forecast", lat, lon);
        List<WeatherForecast> forecasts = new ArrayList<>();

        try {
            JsonNode rootNode = mapper.readTree(responseBody);
            JsonNode listNode = rootNode.get("list");

            if (listNode != null && listNode.isArray()) {
                // Берём прогноз на каждые 24 часа (каждые 8 элементов по 3 часа)
                for (int i = 0; i < listNode.size(); i += 8) {
                    JsonNode item = listNode.get(i);
                    JsonNode mainNode = item.get("main");

                    long timestamp = item.get("dt").asLong();
                    BigDecimal temp = extractTemperature(mainNode);

                    LocalDate date = Instant.ofEpochSecond(timestamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    forecasts.add(new WeatherForecast(date, temp));
                }
            }

            return forecasts;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse OpenWeather forecast response", e);
        } catch (Exception e) {
            throw new RuntimeException("Error processing forecast data", e);
        }
    }

    private String fetchWeatherData(String path, BigDecimal lat, BigDecimal lon) {
        var response = restClient.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParam("appid", "{apiKey}")
                        .queryParam("lat", lat.toString())
                        .queryParam("lon", lon.toString())
                        .queryParam("units", "metric")
                        .build())
                .retrieve()
                .toEntity(String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("OpenWeather returned bad status: " + response.getStatusCode());
        }

        return response.getBody();
    }

    private BigDecimal extractTemperature(JsonNode mainNode) {
        if (mainNode == null || !mainNode.has("temp")) {
            throw new RuntimeException("Missing temperature data in forecast item");
        }
        return new BigDecimal(mainNode.get("temp").asText());
    }
}