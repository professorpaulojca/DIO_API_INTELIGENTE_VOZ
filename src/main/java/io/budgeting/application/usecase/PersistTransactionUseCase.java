package io.budgeting.application.usecase;

import io.budgeting.application.port.TransactionRepository;
import io.budgeting.domain.Transaction;
import io.budgeting.domain.TransactionType;
import io.budgeting.infrastructure.http.request.TransactionRequest;
import io.budgeting.infrastructure.http.response.TransactionResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class PersistTransactionUseCase {

    private final TransactionRepository repository;

    public PersistTransactionUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public TransactionResponse execute(TransactionRequest request) {
        TransactionType type = TransactionType.valueOf(request.type().toUpperCase(Locale.ROOT));
        Transaction transaction = new Transaction(
                null,
                request.description(),
                request.amount(),
                request.category(),
                type,
                LocalDateTime.now()
        );
        Transaction saved = repository.save(transaction);
        return toResponse(saved);
    }

    private TransactionResponse toResponse(Transaction t) {
        return new TransactionResponse(
                t.id(),
                t.description(),
                t.amount(),
                t.category(),
                t.type().name(),
                t.createdAt()
        );
    }
}
