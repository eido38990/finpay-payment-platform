package com.finpay.paymentplatform.service;

import com.finpay.paymentplatform.dto.CreatePaymentMethodRequest;
import com.finpay.paymentplatform.entity.Customer;
import com.finpay.paymentplatform.entity.PaymentMethod;
import com.finpay.paymentplatform.repository.CustomerRepository;
import com.finpay.paymentplatform.repository.PaymentMethodRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentMethodService {
    private final CustomerRepository customerRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    public PaymentMethodService(CustomerRepository customerRepository,PaymentMethodRepository paymentMethodRepository){
        this.customerRepository = customerRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }
    public PaymentMethod createPaymentMethod(CreatePaymentMethodRequest request){
        Customer customer = customerRepository.findById(request.getCustomerId()).
                orElseThrow(()-> new RuntimeException("Customer not found"));
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setPyamentMethodReference("PM_" + UUID.randomUUID().toString().
                substring(0,8).toUpperCase());
        paymentMethod.setCardBrand(request.getCardBrand());
        paymentMethod.setCustomer(customer);
        paymentMethod.setType(request.getType());
        paymentMethod.setLastFour(request.getLastFour());
        paymentMethod.setCreatedAt(String.valueOf(Instant.now()));
        return paymentMethodRepository.save(paymentMethod);
    }
}
