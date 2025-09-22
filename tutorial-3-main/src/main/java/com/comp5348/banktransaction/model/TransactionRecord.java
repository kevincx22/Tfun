package com.comp5348.banktransaction.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity object for transaction_record database table.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private Double amount;

    private String memo;

    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn
    private Account toAccount;

    @ManyToOne
    @JoinColumn
    private Account fromAccount;

    // fields to track merchant fee for business-account transfers

    // amount of merchant fee deducted from this transaction
    // null or 0 if no there is no fee
    private Double feeAmount;

    @ManyToOne
    @JoinColumn
    // the account that received the merchant fee
    // null or 0 if no there is no fee
    private Account feeToAccount;

    @Version
    private int version;

    public TransactionRecord(Double amount, Account toAccount, Account fromAccount, String memo) {
        this.amount = amount;
        this.time = LocalDateTime.now();
        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.memo = memo;
    }
}
