package org.example.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class BankBranch {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private String branchName;
    private String address;
    private String phoneNumber;
    private final List<Employee> employees = new ArrayList<>();
    private final List<Feedback> feedbacks = new ArrayList<>();

    public BankBranch(String branchName, String address, String phoneNumber) {
        this.id = nextId.incrementAndGet();
        this.branchName = branchName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public long getId() {
        return id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public  void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
    }
}
