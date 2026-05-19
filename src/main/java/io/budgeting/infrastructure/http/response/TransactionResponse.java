package io.budgeting.infrastructure.http.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String description,
        BigDecimal amount,
        String category,
        String type,
        LocalDateTime createdAt
) {
}
