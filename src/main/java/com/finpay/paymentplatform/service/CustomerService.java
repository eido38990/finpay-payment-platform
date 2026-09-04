package com.finpay.paymentplatform.service;

import com.finpay.paymentplatform.dto.CreateCustomerRequest;
import com.finpay.paymentplatform.entity.Customer;
import com.finpay.paymentplatform.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    public Customer createCustomer(CreateCustomerRequest request){
        Customer customer = new Customer();
        customer.setCustomerName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setCustomerReference("CUS_" + UUID.randomUUID().toString().substring(0,8).toUpperCase());
        Instant now = Instant.now();
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);
        return customerRepository.save(customer);
    }
}
