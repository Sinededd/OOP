package com.forecast.model;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public enum City {
    MINSK("Минск", new BigDecimal("53.9006"), new BigDecimal("27.5590")),
    LONDON("Лондон", new BigDecimal("51.5074"), new BigDecimal("-0.1278")),
    TOKYO("Токио", new BigDecimal("35.6895"), new BigDecimal("139.6917")),
    SHANGHAI("Шанхай", new BigDecimal("31.2304"), new BigDecimal("121.4737")),
    WARSAW("Варшава", new BigDecimal("52.2297"), new BigDecimal("21.0122"));

    private final String name;
    private final BigDecimal lat;
    private final BigDecimal lon;

    City(String name, BigDecimal lat, BigDecimal lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public static City fromName(String name) {
        for (City city : City.values()) {
            if (city.name.equalsIgnoreCase(name) || city.name().equalsIgnoreCase(name)) {
                return city;
            }
        }
        throw new IllegalArgumentException("City not supported: " + name);
    }
}