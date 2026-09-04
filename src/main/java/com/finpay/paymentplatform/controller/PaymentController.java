package com.finpay.paymentplatform.controller;

import com.finpay.paymentplatform.dto.CreatePaymentRequest;
import com.finpay.paymentplatform.entity.Payment;
import com.finpay.paymentplatform.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController (PaymentService paymentService){
        this.paymentService = paymentService;
    }
    @PostMapping
    public Payment createPayment(@RequestBody CreatePaymentRequest request){
        return paymentService.createPayment(request);
    }
    @PostMapping("/{paymentId}/authorize")
    public Payment authorizePayment(@PathVariable Long paymentId){
        return paymentService.authorizePayment(paymentId);
    }
    @PostMapping("/{paymentId}/capture")
    public Payment capturePayment(@PathVariable Long paymentId){
        return paymentService.capturePayment(paymentId);
    }
    @PostMapping("/{paymentId}/refund")
    public Payment refundPayment(@PathVariable Long paymentId){
        return paymentService.refundPayment(paymentId);
    }

}
