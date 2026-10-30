package com.aymen.transaction.mcp;

import com.aymen.transaction.domain.BankTransaction;
import com.aymen.transaction.service.TransactionService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionMcpTools {

    private final TransactionService service;

    public TransactionMcpTools(TransactionService service) {
        this.service = service;
    }

    @Tool(description = "List all bank transactions")
    public List<BankTransaction> listTransactions() {
        return service.findAll();
    }

    @Tool(description = "Get transactions by account ID")
    public List<BankTransaction> getTransactionsByAccount(
            String accountId) {
        return service.findByAccount(UUID.fromString(accountId));
    }

    @Tool(description = "Create a bank transaction. Requires user confirmation.")
    public BankTransaction createTransaction(
            String accountId,
            double amount,
            String currency,
            String merchantCategory,
            String country) {

        BankTransaction transaction = new BankTransaction();
        transaction.setAccountId(UUID.fromString(accountId));
        transaction.setAmount(BigDecimal.valueOf(amount));
        transaction.setCurrency(currency);
        transaction.setMerchantCategory(merchantCategory);
        transaction.setCountry(country);

        return service.create(transaction);
    }
}