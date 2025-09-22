package com.comp5348.banktransaction.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Entity object for account database table.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @Version
    private int version;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double balance = 0.0;

    //new fields to support business accounts and merchant fee handling
    // account type: PERSONAL or BUSINESS
    // business accounts can have merchant fees
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType = AccountType.PERSONAL;

    // merchant fee rate for business accounts (e.g. 0.02 = 2%)
    // zero for personal accounts
    @Column(nullable = false)
    private Double merchantFeeRate = 0.0;

    // mark the bank's revenue account that collect merchant fees
    // there should be only one such account in the system
    @Column(nullable = false)
    private Boolean isRevenueAccount = false;

    @OneToMany(mappedBy = "fromAccount")
    private Collection<TransactionRecord> fromTransactionRecords;

    @OneToMany(mappedBy = "toAccount")
    private Collection<TransactionRecord> toTransactionRecords;

    public Account(Customer customer, String name) {
        this.customer = customer;
        this.name = name;
        this.fromTransactionRecords = new ArrayList<>();
        this.toTransactionRecords = new ArrayList<>();
    }

    public void modifyBalance(Double amount) {
        this.balance = this.balance + amount;
    }
}
