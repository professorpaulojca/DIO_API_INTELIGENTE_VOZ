package io.budgeting.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        Long id,
        String description,
        BigDecimal amount,
        String category,
        TransactionType type,
        LocalDateTime createdAt
) {
    public Transaction withId(Long newId) {
        return new Transaction(newId, description, amount, category, type, createdAt);
    }
}
