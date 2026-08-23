package com.finpay.pyamentplatform.entity;

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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantStatus status;
    @Column(name = "created_at",nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;


}

