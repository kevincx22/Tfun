package com.comp5348.banktransaction.controller;

import com.comp5348.banktransaction.dto.AccountDTO;
// Task2: allow returning transaction history
import com.comp5348.banktransaction.dto.TransactionRecordDTO;
import com.comp5348.banktransaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Interface
 */
@RestController
@RequestMapping("/api/customer/{customerId}/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(
            @PathVariable Long customerId, @RequestBody CreateAccountRequest request) {
        AccountDTO account = accountService.createAccount(customerId, request.accountName);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccount(
            @PathVariable Long customerId,
            @PathVariable Long accountId) {
        AccountDTO account = accountService.getAccount(customerId, accountId);
        return ResponseEntity.ok(account);
    }

    /**
     * Task2: endpoint to get balance of an account.
     */
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Double> getAccountBalance(@PathVariable Long customerId,
                                                    @PathVariable Long accountId) {
        Double balance = accountService.getAccountBalance(customerId, accountId);
        return ResponseEntity.ok(balance);
    }

    /**
     * Task2: endpoint to retrieve transaction history for an account.
     */
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<Set<TransactionRecordDTO>> getTransactionRecords(
            @PathVariable Long customerId,
            @PathVariable Long accountId) {
        Set<TransactionRecordDTO> records = accountService.getTransactionRecords(customerId, accountId);
        return ResponseEntity.ok(records);
    }

    public static class CreateAccountRequest {
        public String accountName;
    }
}
