package com.finpay.pyamentplatform.entity;

import jakarta.persistence.*;

@Entity
public enum PaymentStatus {
    CREATED,
    AUTHORIZED,
    CAPTURED,
    FAILED,
    CANCELLED,
    REFUNDED
}
