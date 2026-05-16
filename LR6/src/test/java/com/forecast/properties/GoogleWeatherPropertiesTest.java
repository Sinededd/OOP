package com.forecast.properties;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GoogleWeatherPropertiesTest {

    @Test
    void shouldSetAndGetProperties() {
        GoogleWeatherProperties properties = new GoogleWeatherProperties();
        String expectedBaseUrl = "https://weather.google.com";
        String expectedApiKey = "google-secret-key";

        properties.setBaseUrl(expectedBaseUrl);
        properties.setApiKey(expectedApiKey);

        assertThat(properties.getBaseUrl()).isEqualTo(expectedBaseUrl);
        assertThat(properties.getApiKey()).isEqualTo(expectedApiKey);
    }
}