package com.finpay.paymentplatform.ledger;

import com.finpay.paymentplatform.ledger.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry,Long> {
}
