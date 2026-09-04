package com.finpay.paymentplatform.dto;

import com.finpay.paymentplatform.entity.PaymentMethodType;

public class CreatePaymentMethodRequest {
    private Long customerId;
    private PaymentMethodType type;
    private String cardBrand;
    private String lastFour;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
}
