package com.finpay.paymentplatform.repository;

import com.finpay.paymentplatform.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {
}
