package com.forecast.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.forecast.model.City;
import com.forecast.model.WeatherForecast;
import com.forecast.model.WeatherProvider;
import org.springframework.stereotype.Service;

import com.forecast.client.WeatherDataClient;
import com.forecast.model.CurrentWeather;


@Service
public class WeatherService {
    private final Map<WeatherProvider, WeatherDataClient> clients;

    public WeatherService(List<WeatherDataClient> clientList) {
        this.clients = clientList.stream()
                .collect(Collectors.toMap(
                        WeatherDataClient::getProviderType,
                        client -> client
                ));
    }

    public CurrentWeather getCurrentWeather(BigDecimal lat, BigDecimal lon, WeatherProvider provider) {
        WeatherDataClient client = clients.get(provider);
        BigDecimal temperature = client.getCurrentTemperature(lat, lon);
        return new CurrentWeather(temperature);
    }

    public List<WeatherForecast> getForecast(BigDecimal lat, BigDecimal lon, WeatherProvider provider) {
        WeatherDataClient client = clients.get(provider);
        return client.getForecast(lat, lon);
    }

    public Map<String, BigDecimal> getMultipleTemperatures(List<String> cityNames, WeatherProvider provider) {
        return cityNames.stream().collect(Collectors.toMap(
                name -> name,
                name -> {
                    City city = City.fromName(name);
                    return clients.get(provider).getCurrentTemperature(city.getLat(), city.getLon());
                }
        ));
    }
}
