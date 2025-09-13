package com.comp5348.banktransaction.service;

import com.comp5348.banktransaction.dto.AccountDTO;
// Task2: import DTO and model for returning transaction history
import com.comp5348.banktransaction.dto.TransactionRecordDTO;
import com.comp5348.banktransaction.model.Account;
import com.comp5348.banktransaction.model.Customer;
import com.comp5348.banktransaction.model.TransactionRecord;
import com.comp5348.banktransaction.repository.AccountRepository;
import com.comp5348.banktransaction.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Business logic for account management.
 */
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public AccountDTO createAccount(Long customerId, String name) {
        Customer customer = customerRepository.getReferenceById(customerId);
        Account account = new Account(customer, name);
        account = accountRepository.save(account);
        return new AccountDTO(account, true);
    }

    @Transactional
    public AccountDTO getAccount(Long customerId, Long accountId) {
        Account account = accountRepository.findByIdAndCustomer(accountId, customerRepository.getReferenceById(customerId)).orElseThrow();
        return new AccountDTO(account, true);
    }

    /**
     * Task2: retrieve balance of specified account for a customer.
     */
    @Transactional
    public Double getAccountBalance(Long customerId, Long accountId) {
        Account account = accountRepository
                .findByIdAndCustomer(accountId, customerRepository.getReferenceById(customerId))
                .orElseThrow();
        return account.getBalance();
    }

    /**
     * Task2: fetch all transaction records associated with an account.
     */
    @Transactional
    public Set<TransactionRecordDTO> getTransactionRecords(Long customerId, Long accountId) {
        Account account = accountRepository
                .findByIdAndCustomer(accountId, customerRepository.getReferenceById(customerId))
                .orElseThrow();
        Set<TransactionRecordDTO> records = new HashSet<>();
        for (TransactionRecord tr : account.getFromTransactionRecords()) {
            records.add(new TransactionRecordDTO(tr));
        }
        for (TransactionRecord tr : account.getToTransactionRecords()) {
            records.add(new TransactionRecordDTO(tr));
        }
        return records;
    }
}
