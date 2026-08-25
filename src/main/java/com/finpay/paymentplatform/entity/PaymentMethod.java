package com.finpay.paymentplatform.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "payment_method_reference",nullable = false,unique = true)
    private String pyamentMethodReference;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_reference",nullable = false)
    private Customer customer;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethodType type;
    @Column(name = "card_brand")
    private String cardBrand;
    @Column(name = "last_four",length = 4)
    private String lastFour;
    @Column(name = "created_at",nullable = false)
    private String createdAt;
}
