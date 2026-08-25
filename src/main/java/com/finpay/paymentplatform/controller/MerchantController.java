package com.finpay.paymentplatform.controller;

import com.finpay.paymentplatform.entity.Merchant;
import com.finpay.paymentplatform.service.MerchantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/merchant")
public class MerchantController {
    private final MerchantService merchantService;
    public MerchantController(MerchantService merchantService){
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity<Merchant> createdMerchant(@RequestBody Merchant merchant){
        Merchant createdMerchant = merchantService.createMerchant(merchant);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdMerchant);
    }

}
