package com.finpay.paymentplatform.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "merchant_reference", nullable = false, unique = true)
    private String merchantReference;
    @Column(name = "business_name",nullable = false,unique = true)
    private String businessName;
    @Column(nullable = false,unique = true)
    private String email;

    public void setId(Long id) {
        this.id = id;
    }

    public void setMerchantReference(String merchantReference) {
        this.merchantReference = merchantReference;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(MerchantStatus status) {
        this.status = status;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getMerchantReference() {
        return merchantReference;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getEmail() {
        return email;
    }

    public MerchantStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantStatus status;
    @Column(name = "created_at",nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;


}

