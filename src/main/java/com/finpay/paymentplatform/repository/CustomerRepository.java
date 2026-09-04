package com.finpay.paymentplatform.repository;


import com.finpay.paymentplatform.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
