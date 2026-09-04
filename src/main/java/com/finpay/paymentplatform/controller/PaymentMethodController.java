package com.finpay.paymentplatform.controller;

import com.finpay.paymentplatform.dto.CreatePaymentMethodRequest;
import com.finpay.paymentplatform.entity.PaymentMethod;
import com.finpay.paymentplatform.entity.PaymentMethodType;
import com.finpay.paymentplatform.service.PaymentMethodService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment-methods")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;
    public PaymentMethodController(PaymentMethodService paymentMethodService){
        this.paymentMethodService = paymentMethodService;
    }
    @PostMapping
    public PaymentMethod createPaymentMethod(@RequestBody CreatePaymentMethodRequest request){
        return paymentMethodService.createPaymentMethod(request);
    }

}
