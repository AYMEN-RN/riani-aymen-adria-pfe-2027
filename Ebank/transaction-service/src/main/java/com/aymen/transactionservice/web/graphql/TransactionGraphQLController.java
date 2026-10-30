package com.aymen.transaction.web.graphql;

import com.aymen.transaction.domain.BankTransaction;
import com.aymen.transaction.service.TransactionService;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
public class TransactionGraphQLController {

    private final TransactionService service;

    public TransactionGraphQLController(TransactionService service) {
        this.service = service;
    }

    @QueryMapping
    public List<BankTransaction> transactions() {
        return service.findAll();
    }

    @QueryMapping
    public List<BankTransaction> transactionsByAccount(
            @Argument String accountId) {
        return service.findByAccount(UUID.fromString(accountId));
    }

    @MutationMapping
    public BankTransaction createTransaction(
            @Argument String accountId,
            @Argument double amount,
            @Argument String currency,
            @Argument String merchantCategory,
            @Argument String country) {

        BankTransaction transaction = new BankTransaction();
        transaction.setAccountId(UUID.fromString(accountId));
        transaction.setAmount(BigDecimal.valueOf(amount));
        transaction.setCurrency(currency);
        transaction.setMerchantCategory(merchantCategory);
        transaction.setCountry(country);

        return service.create(transaction);
    }
}