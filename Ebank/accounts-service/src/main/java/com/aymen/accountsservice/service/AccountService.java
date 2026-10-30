package com.aymen.accountsservice.service;

import com.aymen.accountservice.domaine.Account;
import com.aymen.accountservice.domaine.AccountStatus;
import com.aymen.accountservice.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found"));
    }

    @Transactional
    public Account create(Account account) {
        account.setStatus(AccountStatus.ACTIVE);
        return repository.save(account);
    }

    @Transactional
    public Account updateBalance(UUID id, BigDecimal delta) {
        Account account = getById(id);

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Account blocked");
        }

        BigDecimal current =
                BigDecimal.valueOf(account.getBalance());

        BigDecimal newBalance = current.add(delta);

        if (newBalance.signum() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        account.setBalance(newBalance.doubleValue());
        return repository.save(account);
    }
}