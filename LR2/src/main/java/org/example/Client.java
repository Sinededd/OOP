package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class Client {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private String fullName;
    private String email = "Undefined";
    private String address = "Undefined";


    public Client(String fullName)
    {
        id = nextId.incrementAndGet();
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

}
