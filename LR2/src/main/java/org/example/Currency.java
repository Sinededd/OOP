package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class Currency {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private String code;
    private double exchangeRateToMain;

    public Currency(String code, double exchangeRateToMain) {
        this.id = nextId.incrementAndGet();
        this.code = code;
        this.exchangeRateToMain = exchangeRateToMain;
    }


    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getExchangeRateToMain() {
        return exchangeRateToMain;
    }

    public void setExchangeRateToMain(double exchangeRateToMain) {
        this.exchangeRateToMain = exchangeRateToMain;
    }
}
