package com.forecast.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WeatherProviderTest {
    @Test
    void shouldHaveTwoProviders() {
        assertEquals(2, WeatherProvider.values().length);
        assertEquals(WeatherProvider.OPEN_WEATHER, WeatherProvider.valueOf("OPEN_WEATHER"));
        assertEquals(WeatherProvider.GOOGLE_WEATHER, WeatherProvider.valueOf("GOOGLE_WEATHER"));
    }
}