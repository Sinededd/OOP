package com.forecast.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forecast.model.WeatherForecast;
import com.forecast.model.WeatherProvider;
import com.forecast.properties.GoogleWeatherProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GoogleWeatherClient implements WeatherDataClient {

    @Qualifier("googleWeatherRestClient")
    private final RestClient restClient;
    private final GoogleWeatherProperties properties;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public WeatherProvider getProviderType() {
        return WeatherProvider.GOOGLE_WEATHER;
    }

    @Override
    public BigDecimal getCurrentTemperature(BigDecimal lat, BigDecimal lon) {
        
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/currentConditions:lookup")
                        .queryParam("location.latitude", lat)
                        .queryParam("location.longitude", lon)
                        .queryParam("key", properties.getApiKey())
                        .build())
                .retrieve()
                .toEntity(String.class);

        
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("GoogleWeather returned bad status: " + responseEntity.getStatusCode());
        }

        String response = responseEntity.getBody();

        try {
            JsonNode root = mapper.readTree(response);
            JsonNode tempNode = root.path("temperature").path("degrees");

            
            if (tempNode.isMissingNode() || tempNode.asText().isEmpty()) {
                tempNode = root.path("main").path("temp");
            }

            if (tempNode.isMissingNode() || tempNode.asText().isEmpty()) {
                throw new RuntimeException("Missing temperature data in GoogleWeather response");
            }
            return new BigDecimal(tempNode.asText());
        } catch (RuntimeException e) {
            throw e; 
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse GoogleWeather current weather response", e);
        }
    }

    @Override
    public List<WeatherForecast> getForecast(BigDecimal lat, BigDecimal lon) {
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/forecast/days:lookup")
                        .queryParam("location.latitude", lat)
                        .queryParam("location.longitude", lon)
                        .queryParam("key", properties.getApiKey())
                        .build())
                .retrieve()
                .toEntity(String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("GoogleWeather returned bad status: " + responseEntity.getStatusCode());
        }

        String response = responseEntity.getBody();
        List<WeatherForecast> forecasts = new ArrayList<>();

        try {
            JsonNode root = mapper.readTree(response);

            
            if (root.has("list")) {
                JsonNode listNode = root.path("list");
                if (listNode.isArray() && listNode.size() > 0) {
                    
                    JsonNode first = listNode.get(0);
                    JsonNode last = listNode.get(listNode.size() - 1);

                    LocalDate firstDate = Instant.ofEpochSecond(first.path("dt").asLong())
                            .atZone(ZoneOffset.UTC).toLocalDate();
                    BigDecimal firstTemp = new BigDecimal(first.path("main").path("temp").asText());

                    LocalDate lastDate = Instant.ofEpochSecond(last.path("dt").asLong())
                            .atZone(ZoneOffset.UTC).toLocalDate();
                    BigDecimal lastTemp = new BigDecimal(last.path("main").path("temp").asText());

                    forecasts.add(new WeatherForecast(firstDate, firstTemp));
                    forecasts.add(new WeatherForecast(lastDate, lastTemp));
                }
            } else {
                
                JsonNode daily = root.path("forecastDays");
                if (daily.isArray()) {
                    for (JsonNode dayNode : daily) {
                        JsonNode d = dayNode.path("displayDate");
                        LocalDate date = LocalDate.of(
                                d.get("year").asInt(),
                                d.get("month").asInt(),
                                d.get("day").asInt()
                        );
                        BigDecimal temp = new BigDecimal(dayNode.path("maxTemperature").path("degrees").asText());
                        forecasts.add(new WeatherForecast(date, temp));
                    }
                }
            }
            return forecasts;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse GoogleWeather forecast response", e);
        }
    }
}