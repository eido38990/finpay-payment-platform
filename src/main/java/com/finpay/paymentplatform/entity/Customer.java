package com.finpay.paymentplatform.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_reference",nullable = false,unique = false)
    private String customerReference;
    @Column(name = "full_name",nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String email;
    @Column(name = "created_at",nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;

}
