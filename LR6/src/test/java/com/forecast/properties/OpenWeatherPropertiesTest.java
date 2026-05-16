package com.forecast.properties;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenWeatherPropertiesTest {

    @Test
    void shouldSetAndGetProperties() {
        OpenWeatherProperties properties = new OpenWeatherProperties();
        String expectedBaseUrl = "https://api.openweathermap.org";
        String expectedApiKey = "my-secret-key";

        properties.setBaseUrl(expectedBaseUrl);
        properties.setApiKey(expectedApiKey);

        assertThat(properties.getBaseUrl()).isEqualTo(expectedBaseUrl);
        assertThat(properties.getApiKey()).isEqualTo(expectedApiKey);
    }
}