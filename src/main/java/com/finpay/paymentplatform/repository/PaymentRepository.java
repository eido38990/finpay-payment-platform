package com.finpay.paymentplatform.repository;

import com.finpay.paymentplatform.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
