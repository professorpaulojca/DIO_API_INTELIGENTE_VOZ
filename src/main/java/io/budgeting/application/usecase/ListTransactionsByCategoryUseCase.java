package io.budgeting.application.usecase;

import io.budgeting.application.port.TransactionRepository;
import io.budgeting.domain.Transaction;
import io.budgeting.infrastructure.http.response.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTransactionsByCategoryUseCase {

    private final TransactionRepository repository;

    public ListTransactionsByCategoryUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<TransactionResponse> execute(String category) {
        return repository.findByCategory(category)
                .stream()
                .map(this::toResponse)
                .toList();
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
