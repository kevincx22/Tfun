package com.comp5348.banktransaction.dto;

import com.comp5348.banktransaction.model.Account;
import com.comp5348.banktransaction.model.TransactionRecord;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for TransactionRecord.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionRecordDTO {
    private long id;
    private Double amount;
    private String memo;
    private LocalDateTime time;
    private AccountDTO toAccount;
    private AccountDTO fromAccount;
    // extra fields for merchant-fee support
    // record how much fee was deducted and which account received it

    //The fee amount deducted from the gross transfer amount. Null if there is no fee.
    private Double feeAmount;

    // The account that received the fee. Null if there is no fee.
    private AccountDTO feeToAccount;

    public TransactionRecordDTO(TransactionRecord entity) {
        this.id = entity.getId();
        this.memo = entity.getMemo();
        this.amount = entity.getAmount();
        this.time = entity.getTime();
        Account toAccount = entity.getToAccount();
        if (toAccount != null) {
            this.toAccount = new AccountDTO(toAccount);
        }
        Account fromAccount = entity.getFromAccount();
        if (fromAccount != null) {
            this.fromAccount = new AccountDTO(fromAccount);
        }

        // set fee info if this transaction involved a merchant fee.
        // copy the fee amount and the revenue account that received it
        if (entity.getFeeAmount() != null) {
            this.feeAmount = entity.getFeeAmount();
        }
        Account feeTo = entity.getFeeToAccount();
        if (feeTo != null) {
            this.feeToAccount = new AccountDTO(feeTo);
        }
    }
}
