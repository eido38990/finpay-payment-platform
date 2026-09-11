package com.finpay.paymentplatform.ledger;

import com.finpay.paymentplatform.entity.Payment;
import jakarta.persistence.*;

@Entity
@Table(name = "ledger_account")
public class LedgerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_reference", unique = true, nullable = false)
    private String accountReference;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountReference() {
        return accountReference;
    }

    public void setAccountReference(String accountReference) {
        this.accountReference = accountReference;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
