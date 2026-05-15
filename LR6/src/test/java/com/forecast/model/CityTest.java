package com.forecast.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

class CityTest {
    @Test
    void fromName_shouldReturnCityByRussianName() {
        assertEquals(City.MINSK, City.fromName("Минск"));
        assertEquals(City.LONDON, City.fromName("Лондон"));
    }

    @Test
    void fromName_shouldBeCaseInsensitive() {
        assertEquals(City.MINSK, City.fromName("minsk"));
        assertEquals(City.MINSK, City.fromName("МИНСК"));
    }

    @Test
    void fromName_shouldThrowExceptionForUnknownCity() {
        assertThrows(IllegalArgumentException.class, () -> City.fromName("Paris"));
    }
}