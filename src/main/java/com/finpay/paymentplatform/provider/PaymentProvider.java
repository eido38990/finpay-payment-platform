package com.finpay.paymentplatform.provider;

import com.finpay.paymentplatform.entity.Payment;

public interface PaymentProvider {
    boolean authorize(Payment payment);
    boolean capture(Payment payment);
    boolean refund(Payment payment);
}
