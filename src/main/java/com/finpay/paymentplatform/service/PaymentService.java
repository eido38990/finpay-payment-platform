package com.finpay.paymentplatform.service;

import com.finpay.paymentplatform.dto.CreatePaymentMethodRequest;
import com.finpay.paymentplatform.dto.CreatePaymentRequest;
import com.finpay.paymentplatform.entity.*;
import com.finpay.paymentplatform.exception.InvalidPaymentStateException;
import com.finpay.paymentplatform.exception.ResourceNotFoundException;
import com.finpay.paymentplatform.ledger.LedgerService;
import com.finpay.paymentplatform.provider.PaymentProvider;
import com.finpay.paymentplatform.repository.CustomerRepository;
import com.finpay.paymentplatform.repository.MerchantRepository;
import com.finpay.paymentplatform.repository.PaymentMethodRepository;
import com.finpay.paymentplatform.repository.PaymentRepository;
import jakarta.transaction.Transactional;
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
    private final LedgerService ledgerService;
    public PaymentService(PaymentRepository paymentRepository,CustomerRepository customerRepository,
                          MerchantRepository merchantRepository,PaymentMethodRepository paymentMethodRepository,
                          PaymentProvider paymentProvider,LedgerService ledgerService){
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        this.merchantRepository = merchantRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentProvider = paymentProvider;
        this.ledgerService = ledgerService;
    }
    @Transactional
    public Payment createPayment(CreatePaymentRequest request){
        Payment existingPayment = paymentRepository.findByIdempotencyKey(request.getIdempotencyKey())
                .orElse(null);
        if (existingPayment != null){
            boolean sameRequest = existingPayment.getMerchant().getId()
                    .equals(request.getMerchantId())
                    &&
                    existingPayment.getCustomer().getId()
                            .equals(request.getCustomerId())
                    &&
                    existingPayment.getPayment()
                            .equals(request.getAmount())
                    &&
                    existingPayment.getPaymentMethod().getId()
                            .equals(request.getPaymentMethodId())
                    &&
                    existingPayment.getCurrency()
                            .equalsIgnoreCase(request.getCurrency());
            if (!sameRequest){
                throw new RuntimeException("Idempotency key already used with different payment details");
            }
            return existingPayment;
        }
        Merchant merchant = merchantRepository.findById(request.getMerchantId())
                .orElseThrow(()-> new ResourceNotFoundException("Merchant not Found"));
        if (merchant.getStatus() != MerchantStatus.ACTIVE){
            throw new InvalidPaymentStateException("Merchant not active");
        }
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(()-> new ResourceNotFoundException("Customer not Found"));
        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(()-> new ResourceNotFoundException("Payment Method not Found"));
        if (!paymentMethod.getCustomer().getId().equals(request.getCustomerId())){
            throw new ResourceNotFoundException("Payment method does not belong to this customer");
        }
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
        payment.setIdempotencyKey(request.getIdempotencyKey());
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment authorizePayment(Long paymentId){
        Payment payment = paymentRepository.findByIdForUpdate(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not Found"));
        if (payment.getStatus() != PaymentStatus.CREATED){
            throw new InvalidPaymentStateException("Only CREATED payments can be authorized");
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

    @Transactional
    public Payment capturePayment(Long paymentId){
        Payment payment = paymentRepository.findByIdForUpdate(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        if (payment.getStatus() != PaymentStatus.AUTHORIZED){
            throw new InvalidPaymentStateException("Only AUTHORIZED payments can be captured");
        }
        boolean capture = paymentProvider.capture(payment);
        if (capture){
            payment.setStatus(PaymentStatus.CAPTURED);
            paymentRepository.save(payment);
            ledgerService.recordCapture(payment);
        }else {
            payment.setStatus(PaymentStatus.FAILED);
        }
        payment.setStatus(PaymentStatus.CAPTURED);
        payment.setUpdatedAt(Instant.now());
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment refundPayment(Long paymentId){
        Payment payment = paymentRepository.findByIdForUpdate(paymentId)
                .orElseThrow(()-> new ResourceNotFoundException("Payment not found"));
        if (payment.getStatus() != PaymentStatus.CAPTURED){
            throw new InvalidPaymentStateException("Only Captured Payments can be refunded");
        }
        boolean refund = paymentProvider.refund(payment);
        if (refund){
            payment.setStatus(PaymentStatus.REFUNDED);

        }else {
            throw new RuntimeException("Refund Failed");
        }
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setUpdatedAt(Instant.now());
        paymentRepository.save(payment);
        ledgerService.recordRefund(payment);
        return payment;
    }
}
