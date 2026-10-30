package com.aymen.transactionservice.service;

import com.aymen.transactionservice.client.AccountClient;
import com.aymen.transactionservice.domaine.BankTransaction;
import com.aymen.transactionservice.dto.AccountDTO;
import com.aymen.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountClient accountClient;

    @Value("${bank.transaction.max-amount}")
    private BigDecimal maxAmount;

    public TransactionService(TransactionRepository repository,
                              AccountClient accountClient) {
        this.repository = repository;
        this.accountClient = accountClient;
    }

    @Transactional
    public BankTransaction create(BankTransaction transaction) {

        BigDecimal amount = transaction.getAmount();

        if (amount == null || amount.signum() <= 0
                || amount.compareTo(maxAmount) > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid transaction amount");
        }

        if (transaction.getAccountId() == null
                || transaction.getCurrency() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Missing account or currency");
        }

        AccountDTO account =
                accountClient.getAccount(transaction.getAccountId());

        if (!"ACTIVE".equals(account.status())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Account is blocked");
        }

        if (!transaction.getCurrency().equals(account.currency())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Currency mismatch");
        }

        if (BigDecimal.valueOf(account.balance())
                .compareTo(amount) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        // Debit du compte
        accountClient.updateBalance(
                transaction.getAccountId(), amount.negate());

        return repository.save(transaction);
    }

    public List<BankTransaction> findAll() {
        return repository.findAll();
    }

    public List<BankTransaction> findByAccount(UUID accountId) {
        return repository.findByAccountId(accountId);
    }
}