package com.aymen.accountservice.web.rest;

import com.aymen.accountsservice.domaine.Account;
import com.aymen.accountsservice.service.AccountService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    private final AccountService service;

    public AccountRestController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public List<Account> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public Account create(@RequestBody Account account) {
        return service.create(account);
    }

    @PutMapping("/{id}/balance")
    public Account updateBalance(
            @PathVariable UUID id,
            @RequestParam BigDecimal delta) {
        return service.updateBalance(id, delta);
    }
}