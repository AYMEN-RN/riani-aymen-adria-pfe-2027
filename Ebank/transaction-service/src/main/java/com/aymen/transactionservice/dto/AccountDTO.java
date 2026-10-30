package com.aymen.transactionservice.dto;

import java.util.UUID;

public record AccountDTO(
        UUID id,
        String customerReference,
        String status,
        String currency,
        double balance
) {}