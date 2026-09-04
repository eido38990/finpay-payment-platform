package com.finpay.paymentplatform.service;

import com.finpay.paymentplatform.dto.CreatePaymentMethodRequest;
import com.finpay.paymentplatform.dto.CreatePaymentRequest;
import com.finpay.paymentplatform.entity.*;
import com.finpay.paymentplatform.provider.PaymentProvider;
import com.finpay.paymentplatform.repository.CustomerRepository;
import com.finpay.paymentplatform.repository.MerchantRepository;
import com.finpay.paymentplatform.repository.PaymentMethodRepository;
import com.finpay.paymentplatform.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final MerchantRepository merchantRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentProvider paymentProvider;
    public PaymentService(PaymentRepository paymentRepository,CustomerRepository customerRepository,
                          MerchantRepository merchantRepository,PaymentMethodRepository paymentMethodRepository,
                          PaymentProvider paymentProvider){
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        this.merchantRepository = merchantRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentProvider = paymentProvider;
    }
    public Payment createPayment(CreatePaymentRequest request){
        Merchant merchant = merchantRepository.findById(request.getMerchantId())
                .orElseThrow(()-> new RuntimeException("Merchant not Found"));
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(()-> new RuntimeException("Customer not Found"));
        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(()-> new RuntimeException("Payment Method not Found"));
        Payment payment = new Payment();
        payment.setPaymentReference("PAY_" + UUID.randomUUID().toString().substring(0,8).toUpperCase());
        payment.setMerchant(merchant);
        payment.setCustomer(customer);
        payment.setPaymentMethod(paymentMethod);
        payment.setPayment(request.getAmount());
        payment.setCreateAt(Instant.now());
        payment.setCurrency(request.getCurrency().toUpperCase());
        payment.setStatus(PaymentStatus.CREATED);
        payment.setUpdatedAt(Instant.now());
        return paymentRepository.save(payment);
    }

    public Payment authorizePayment(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not Found"));
        if (payment.getStatus() != PaymentStatus.CREATED){
            throw new RuntimeException("Only CREATED payments can be authorized");
        }
        boolean approved = paymentProvider.authorize(payment);
        if (approved){
            payment.setStatus(PaymentStatus.AUTHORIZED);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }
        payment.setUpdatedAt(Instant.now());
        return paymentRepository.save(payment);
    }

    public Payment capturePayment(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        if (payment.getStatus() != PaymentStatus.AUTHORIZED){
            throw new RuntimeException("Only AUTHORIZED payments can be captured");
        }
        boolean capture = paymentProvider.capture(payment);
        if (capture){
            payment.setStatus(PaymentStatus.CAPTURED);
        }else {
            payment.setStatus(PaymentStatus.FAILED);
        }
        payment.setStatus(PaymentStatus.CAPTURED);
        payment.setUpdatedAt(Instant.now());
        return paymentRepository.save(payment);
    }

    public Payment refundPayment(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(()-> new RuntimeException("Payment not found"));
        if (payment.getStatus() != PaymentStatus.CAPTURED){
            throw new RuntimeException("Only Captured Payments can be refunded");
        }
        boolean refund = paymentProvider.refund(payment);
        if (refund){
            payment.setStatus(PaymentStatus.REFUNDED);
        }else {
            throw new RuntimeException("Refund Failed")
        }
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setUpdatedAt(Instant.now());
        return paymentRepository.save(payment);
    }
}
