package com.finpay.pyamentplatform.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "payment_reference",nullable = false,unique = true)
    private String paymentReference;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_reference",nullable = false)
    private Merchant merchant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_reference",nullable = false)
    private Customer customer;
    @Column(nullable = false)
    private Long payment;
    @Column(nullable = false,length = 3)
    private String currency;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;
    @Column(name = "create_at",nullable = false)
    private Instant createAt;
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;
}
