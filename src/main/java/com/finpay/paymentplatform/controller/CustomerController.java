package com.finpay.paymentplatform.controller;


import com.finpay.paymentplatform.dto.CreateCustomerRequest;
import com.finpay.paymentplatform.entity.Customer;
import com.finpay.paymentplatform.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @PostMapping
    public Customer createCustomer(@RequestBody CreateCustomerRequest request){
        return customerService.createCustomer(request);
    }
}
