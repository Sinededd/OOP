package org.example.bank;

import java.util.concurrent.atomic.AtomicLong;

public class Feedback {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private long clientId;
    private String message;
    private int rating;

    public Feedback(long clientId, String message, int rating) {
        this.id = nextId.incrementAndGet();
        this.clientId = clientId;
        this.message = message;
        this.rating = rating;
    }


    public long getId() {
        return id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
