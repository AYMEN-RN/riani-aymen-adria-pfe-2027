package com.aymen.accountservice.mcp;

import com.aymen.accountservice.domaine.Account;
import com.aymen.accountservice.service.AccountService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AccountMcpTools {

    private final AccountService service;

    public AccountMcpTools(AccountService service) {
        this.service = service;
    }

    @Tool(description = "Get bank account by its ID")
    public Account getAccount(String accountId) {
        return service.getById(UUID.fromString(accountId));
    }

    @Tool(description = "List all bank accounts")
    public List<Account> listAccounts() {
        return service.findAll();
    }

    @Tool(description = "Get account balance")
    public double getAccountBalance(String accountId) {
        return service.getById(
                UUID.fromString(accountId)
        ).getBalance();
    }
}