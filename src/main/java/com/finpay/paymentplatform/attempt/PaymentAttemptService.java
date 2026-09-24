package com.finpay.paymentplatform.attempt;

import com.finpay.paymentplatform.entity.Payment;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentAttemptService {
    private final PaymentAttemptRepository repository;

    public PaymentAttemptService(PaymentAttemptRepository repository){
        this.repository = repository;
    }
    public void recordSuccess(
            Payment payment,
            PaymentAttemptType type,
            String providerReference) {

        PaymentAttempt attempt = new PaymentAttempt();

        attempt.setPayment(payment);
        attempt.setAttemptType(type);
        attempt.setProviderName("FAKE_PROVIDER");
        attempt.setProviderReference(providerReference);
        attempt.setStatus(PaymentAttemptStatus.SUCCESS);
        attempt.setCreatedAt(Instant.now());

        repository.save(attempt);
    }

    public void recordFailure(
            Payment payment,
            PaymentAttemptType type,
            String reason) {

        PaymentAttempt attempt = new PaymentAttempt();

        attempt.setPayment(payment);
        attempt.setAttemptType(type);
        attempt.setProviderName("FAKE_PROVIDER");
        attempt.setStatus(PaymentAttemptStatus.FAILED);
        attempt.setFailureReason(reason);
        attempt.setCreatedAt(Instant.now());

        repository.save(attempt);
    }
}
