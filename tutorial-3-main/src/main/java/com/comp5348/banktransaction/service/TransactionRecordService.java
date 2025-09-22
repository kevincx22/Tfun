package com.comp5348.banktransaction.service;

import com.comp5348.banktransaction.dto.TransactionRecordDTO;
import com.comp5348.banktransaction.errors.InsufficientBalanceException;
import com.comp5348.banktransaction.errors.NegativeTransferAmountException;
import com.comp5348.banktransaction.model.Account;
import com.comp5348.banktransaction.model.TransactionRecord;
import com.comp5348.banktransaction.model.AccountType;
import com.comp5348.banktransaction.repository.AccountRepository;
import com.comp5348.banktransaction.repository.CustomerRepository;
import com.comp5348.banktransaction.repository.TransactionRecordRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Business logic for creating and managing transactions (transfer / deposit).
 */
@Service
public class TransactionRecordService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public TransactionRecordService(AccountRepository accountRepository, CustomerRepository customerRepository, TransactionRecordRepository transactionRecordRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Transactional
    public TransactionRecordDTO performTransaction(
            Long fromCustomerId, Long fromAccountId,
            Long toCustomerId, Long toAccountId,
            Double amount, String memo)
            throws InsufficientBalanceException, HttpClientErrorException {
        if (amount <= 0) {
            throw new NegativeTransferAmountException();
        }

        Account fromAccount = null;
        if (fromAccountId != null) {
            fromAccount = accountRepository
                    .findByIdAndCustomer(fromAccountId, customerRepository.getReferenceById(fromCustomerId))
                    .orElseThrow();
            entityManager.refresh(fromAccount);

            if (fromAccount.getBalance() < amount) {
                throw new InsufficientBalanceException();
            }
            fromAccount.modifyBalance(-amount);
            accountRepository.save(fromAccount);
        }
        Account toAccount = null;
        // Variables to track fee info
        Double fee = null;
        Account feeToAccount = null;
        if (toAccountId != null) {
            toAccount = accountRepository
                    .findByIdAndCustomer(toAccountId, customerRepository.getReferenceById(toCustomerId))
                    .orElseThrow();
// handle merchant fee when transferring to a business account
// if it is BUSINESS and has a positive fee rate:
//   - compute fee and reduce the credited amount
//   - locate the bank's revenue account and add the fee there
//   - credit the remaining amount to the recipient account
            double creditAmount = amount;
            // Apply merchant fee only for account-to-account transfers when recipient is business
            if (fromAccount != null && toAccount.getAccountType() == AccountType.BUSINESS) {
                Double rate = toAccount.getMerchantFeeRate();
                if (rate != null && rate > 0.0) {
                    fee = amount * rate;
                    creditAmount = amount - fee;
                    // locate revenue account
                    feeToAccount = accountRepository.findByIsRevenueAccountTrue().orElse(null);
                    if (feeToAccount == null) {
                        throw new IllegalStateException("No revenue account configured");
                    }
                    // credit fee to revenue account
                    feeToAccount.modifyBalance(fee);
                    accountRepository.save(feeToAccount);
                }
            }
            toAccount.modifyBalance(creditAmount);
            accountRepository.save(toAccount);
        }

        // store fee info along with the transaction
        // if there is a merchant fee, record the fee amount and the revenue account that received it
        TransactionRecord transactionRecord = new TransactionRecord(amount, toAccount, fromAccount, memo);
        if (fee != null) {
            transactionRecord.setFeeAmount(fee);
            transactionRecord.setFeeToAccount(feeToAccount);
        }
        transactionRecordRepository.save(transactionRecord);

        return new TransactionRecordDTO(transactionRecord);
    }
}
