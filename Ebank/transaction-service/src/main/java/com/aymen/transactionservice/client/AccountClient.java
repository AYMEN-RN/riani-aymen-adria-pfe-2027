package com.aymen.transactionservice.client;

import com.aymen.transactionservice.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@FeignClient(name = "account-service",
        url = "${account.service.url}")
public interface AccountClient {

    @GetMapping("/api/accounts/{id}")
    AccountDTO getAccount(@PathVariable("id") UUID id);

    @PutMapping("/api/accounts/{id}/balance")
    AccountDTO updateBalance(
            @PathVariable("id") UUID id,
            @RequestParam("delta") BigDecimal delta);
}