package com.finpay.paymentplatform.ledger;

import com.finpay.paymentplatform.entity.Payment;
import com.finpay.paymentplatform.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LedgerService {
    private final LedgerAccountRepository accountRepository;
    private final LedgerEntryRepository entryRepository;
    public LedgerService (LedgerAccountRepository accountRepository,LedgerEntryRepository entryRepository){
        this.accountRepository = accountRepository;
        this.entryRepository = entryRepository;
    }
    public void recordCapture(Payment payment){
        LedgerAccount clearingAccount = accountRepository.findByAccountReference("FINPAY_CLEARING")
                .orElseThrow(()-> new ResourceNotFoundException("clearing account not found"));
        String merchantAccountReference = "MERCHANT_" + payment.getMerchant().getId();
        LedgerAccount merchantAccount = accountRepository.findByAccountReference(merchantAccountReference)
                .orElseThrow(()-> new ResourceNotFoundException("Merchant ledger account not found"));
        createEntry(payment,
                clearingAccount,
                payment.getPayment(),
                payment.getCurrency(),
                LedgerEntryType.DEBIT);
        createEntry(payment,
                merchantAccount,
                payment.getPayment(),
                payment.getCurrency(),
                LedgerEntryType.CREDIT);
    }
    private void createEntry(Payment payment,
                             LedgerAccount account,
                             Long amount,
                             String currency,
                             LedgerEntryType type){
        LedgerEntry entry = new LedgerEntry();

        entry.setPayment(payment);
        entry.setAccount(account);
        entry.setAmount(amount);
        entry.setCurrency(currency);
        entry.setEntryType(type);
        entry.setCreatedAt(Instant.now());

        entryRepository.save(entry);
    }

    public void recordRefund(Payment payment){
        LedgerAccount clearingAccount = accountRepository.findByAccountReference("FINPAY_CLEARING")
                .orElseThrow(()-> new RuntimeException("Clearing account not found"));
        String merchantAccountReference = "MERCHANT_" + payment.getMerchant().getId();
        LedgerAccount merchantAccount = accountRepository.findByAccountReference(merchantAccountReference)
                .orElseThrow(()-> new RuntimeException("Merchant ledger account not found"));
        createEntry(payment,
                merchantAccount,
                payment.getPayment(),
                payment.getCurrency(),
                LedgerEntryType.DEBIT);
        createEntry(payment,
                clearingAccount,
                payment.getPayment(),
                payment.getCurrency(),
                LedgerEntryType.CREDIT);
    }
}
