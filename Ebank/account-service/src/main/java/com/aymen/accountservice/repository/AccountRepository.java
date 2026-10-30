package com.aymen.accountservice.repository;

import com.aymen.accountsservice.domaine.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AccountRepository
        extends JpaRepository<Account, UUID> {
}