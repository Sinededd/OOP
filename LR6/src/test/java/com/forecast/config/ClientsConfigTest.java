package com.forecast.config;

import com.forecast.properties.GoogleWeatherProperties;
import com.forecast.properties.OpenWeatherProperties;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

class ClientsConfigTest {

    private final ClientsConfig clientsConfig = new ClientsConfig();

    @Test
    void openWeatherRestClient_ShouldReturnConfiguredClient() {
        OpenWeatherProperties properties = new OpenWeatherProperties();
        properties.setBaseUrl("https://api.openweathermap.org");
        properties.setApiKey("test-open-key");

        RestClient client = clientsConfig.openWeatherRestClient(properties);

        assertThat(client).isNotNull();
    }

    @Test
    void googleWeatherRestClient_ShouldReturnConfiguredClient() {
        GoogleWeatherProperties properties = new GoogleWeatherProperties();
        properties.setBaseUrl("https://weather.google.com");
        properties.setApiKey("test-google-key");

        RestClient client = clientsConfig.googleWeatherRestClient(properties);

        assertThat(client).isNotNull();
    }
}