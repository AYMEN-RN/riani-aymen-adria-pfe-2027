package com.aymen.transactionservice.web.rest;

import com.aymen.transactionservice.domaine.BankTransaction;
import com.aymen.transactionservice.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    private final TransactionService service;

    public TransactionRestController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public BankTransaction create(@RequestBody BankTransaction transaction) {
        return service.create(transaction);
    }

    @GetMapping
    public List<BankTransaction> all() {
        return service.findAll();
    }

    @GetMapping("/account/{accountId}")
    public List<BankTransaction> byAccount(
            @PathVariable UUID accountId) {
        return service.findByAccount(accountId);
    }
}