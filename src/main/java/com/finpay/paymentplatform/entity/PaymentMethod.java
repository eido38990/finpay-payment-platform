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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPyamentMethodReference() {
        return pyamentMethodReference;
    }

    public void setPyamentMethodReference(String pyamentMethodReference) {
        this.pyamentMethodReference = pyamentMethodReference;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PaymentMethodType getType() {
        return type;
    }

    public void setType(PaymentMethodType type) {
        this.type = type;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "last_four",length = 4)
    private String lastFour;
    @Column(name = "created_at",nullable = false)
    private String createdAt;
}
