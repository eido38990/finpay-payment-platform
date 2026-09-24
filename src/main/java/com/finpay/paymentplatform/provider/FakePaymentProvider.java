package com.finpay.paymentplatform.provider;

import com.finpay.paymentplatform.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class FakePaymentProvider implements PaymentProvider{
    @Override
    public ProviderResult authorize(Payment payment){
        if (payment.getPayment() > 100000){
            return new ProviderResult(false,
                    null,
                    "Payment amount exceeds fake provider authorization limit");
        }
        return successfulResult();
    }
    @Override
    public ProviderResult capture(Payment payment) {
        return successfulResult();
    }

    @Override
    public ProviderResult refund(Payment payment) {
        return successfulResult();
    }
    private ProviderResult successfulResult(){
        String reference = "FP_" + java.util.UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
        return new ProviderResult(true,
                reference,
                null);
    }

}
