package com.finpay.paymentplatform.provider;

import com.finpay.paymentplatform.entity.Payment;

public interface PaymentProvider {
    ProviderResult authorize(Payment payment);

    ProviderResult capture(Payment payment);

    ProviderResult refund(Payment payment);
}
