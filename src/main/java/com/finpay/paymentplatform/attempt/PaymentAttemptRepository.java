package com.finpay.paymentplatform.attempt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentAttemptRepository extends JpaRepository<PaymentAttempt,Long> {
}
