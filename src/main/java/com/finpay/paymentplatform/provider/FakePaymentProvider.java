package com.finpay.paymentplatform.provider;

import com.finpay.paymentplatform.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class FakePaymentProvider implements PaymentProvider{
    @Override
    public boolean authorize(Payment payment){
        return payment.getPayment() <= 10000;
    }
    @Override
    public boolean capture(Payment payment){
        return true;
    }
    @Override
    public boolean refund(Payment payment){
        return true;
    }

}
