package com.finpay.paymentplatform.dto;

public class CreateCustomerRequest {
    private String fullName;
    private String email;

    public String getFullName() {
        return fullName;
    }

    public void setFulName(String fulName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
