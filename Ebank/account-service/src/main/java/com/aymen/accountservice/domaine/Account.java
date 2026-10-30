package com.aymen.accountservice.domaine;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String customerReference;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private String currency;

    private double balance;

    @Version
    private Long version;

    public Account() {}

    public UUID getId() { return id; }
    public String getCustomerReference() { return customerReference; }
    public AccountStatus getStatus() { return status; }
    public String getCurrency() { return currency; }
    public double getBalance() { return balance; }

    public void setCustomerReference(String ref) {
        this.customerReference = ref;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
