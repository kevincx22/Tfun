package com.comp5348.banktransaction.service;

import com.comp5348.banktransaction.dto.AccountDTO;
import com.comp5348.banktransaction.model.Account;
import com.comp5348.banktransaction.model.Customer;
import com.comp5348.banktransaction.model.AccountType;
import com.comp5348.banktransaction.repository.AccountRepository;
import com.comp5348.banktransaction.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public AccountDTO createAccount(Long customerId,
                                    String name,
                                    String accountType,
                                    Double merchantFeeRate,
                                    Boolean isRevenueAccount) {
        Customer customer = customerRepository.getReferenceById(customerId);
        Account account = new Account(customer, name);

        // set up new account properties for merchant-fee support
        // decide account type, merchant fee rate and whether it's the single revenue account
        // all these values are stored on the Account entity when creating a new account
        // determine account type(PERSONAL by default, BUSINESS if specified and valid)
        AccountType type = AccountType.PERSONAL;
        if (accountType != null) {
            try {
                type = AccountType.valueOf(accountType.toUpperCase());
            } catch (IllegalArgumentException ex) {
                // invalid input defaults to PERSONAL
                type = AccountType.PERSONAL;
            }
        }
        account.setAccountType(type);

        // set merchant fee rate if it's a business account
        Double feeRate = 0.0;
        if (type == AccountType.BUSINESS) {
            if (merchantFeeRate != null && merchantFeeRate >= 0.0 && merchantFeeRate < 1.0) {
                feeRate = merchantFeeRate;
            }
        }
        account.setMerchantFeeRate(feeRate);

        // mark revenue account if requested and make sure there is only one
        boolean revenueFlag = Boolean.TRUE.equals(isRevenueAccount);
        if (revenueFlag) {
            // ensure there is no existing revenue account
            accountRepository.findByIsRevenueAccountTrue().ifPresent(existing -> {
                throw new IllegalStateException("Revenue account already exists with id " + existing.getId());
            });
        }
        account.setIsRevenueAccount(revenueFlag);

        account = accountRepository.save(account);
        return new AccountDTO(account, true);
    }

    @Transactional
    public AccountDTO getAccount(Long customerId, Long accountId) {
        Account account = accountRepository.findByIdAndCustomer(accountId, customerRepository.getReferenceById(customerId)).orElseThrow();
        return new AccountDTO(account, true);
    }
}
