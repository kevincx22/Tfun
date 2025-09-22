package com.comp5348.banktransaction.controller;

import com.comp5348.banktransaction.dto.AccountDTO;
import com.comp5348.banktransaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    // createAccount now support creating both personal and business accounts
    // Add accountType, merchantFeeRate and isRevenueAccount to handle merchant fee logic
    // and allow marking the unique bank revenue account
    public ResponseEntity<AccountDTO> createAccount(
            @PathVariable Long customerId, @RequestBody CreateAccountRequest request) {
        AccountDTO account = accountService.createAccount(
                customerId,
                request.accountName,
                request.accountType,
                request.merchantFeeRate,
                request.isRevenueAccount
        );
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccount(
            @PathVariable Long customerId,
            @PathVariable Long accountId) {
        AccountDTO account = accountService.getAccount(customerId, accountId);
        return ResponseEntity.ok(account);
    }
    // request body for creating an account
    // holds new fields needed for personal /or business account support,
    // merchant fee rate and if is revenue account
    public static class CreateAccountRequest {
        public String accountName;
        public String accountType;
        public Double merchantFeeRate;
        public Boolean isRevenueAccount;
    }
}
