package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class Employee {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private String fullName;
    private String position;
    private double salary;

    public Employee(String fullName, String position, double salary)
    {
        this.id = nextId.incrementAndGet();
        this.fullName = fullName;
        this.position = position;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if(salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }
}
