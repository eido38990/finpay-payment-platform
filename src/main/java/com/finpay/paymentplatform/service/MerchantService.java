package com.finpay.paymentplatform.service;

import com.finpay.paymentplatform.dto.CreateMerchantRequest;
import com.finpay.paymentplatform.entity.Merchant;
import com.finpay.paymentplatform.entity.MerchantStatus;
import com.finpay.paymentplatform.repository.MerchantRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class MerchantService {
    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository){
        this.merchantRepository = merchantRepository;
    }

    public Merchant createMerchant(CreateMerchantRequest request){
        Merchant merchant = new Merchant();
        merchant.setBusinessName(request.getBusinessName());
        merchant.setEmail(request.getEmail());
        merchant.setMerchantReference("MER_" + UUID.randomUUID().toString().substring(0,8).toUpperCase());
        merchant.setStatus(MerchantStatus.ACTIVE);
        Instant now = Instant.now();
        merchant.setCreatedAt(now);
        merchant.setUpdatedAt(now);
        return merchantRepository.save(merchant);
    }


}
