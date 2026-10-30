package com.aymen.accountsservice.web.graphql;

import com.aymen.accountservice.domaine.Account;
import com.aymen.accountservice.service.AccountService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class AccountGraphQLController {

    private final AccountService service;

    public AccountGraphQLController(AccountService service) {
        this.service = service;
    }

    @QueryMapping
    public List<Account> accounts() {
        return service.findAll();
    }

    @QueryMapping
    public Account accountById(@Argument String id) {
        return service.getById(UUID.fromString(id));
    }

    @MutationMapping
    public Account createAccount(
            @Argument String customerReference,
            @Argument String currency,
            @Argument double balance) {

        Account account = new Account();
        account.setCustomerReference(customerReference);
        account.setCurrency(currency);
        account.setBalance(balance);

        return service.create(account);
    }
}