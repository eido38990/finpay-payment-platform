package com.finpay.paymentplatform.repository;

import com.finpay.paymentplatform.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {
}
