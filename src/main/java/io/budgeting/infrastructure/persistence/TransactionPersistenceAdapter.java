package io.budgeting.infrastructure.persistence;

import io.budgeting.application.port.TransactionRepository;
import io.budgeting.domain.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionPersistenceAdapter implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;

    public TransactionPersistenceAdapter(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionJpaEntity entity = toEntity(transaction);
        TransactionJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Transaction> findByCategory(String category) {
        return jpaRepository.findByCategory(category)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private TransactionJpaEntity toEntity(Transaction transaction) {
        return new TransactionJpaEntity(
                transaction.id(),
                transaction.description(),
                transaction.amount(),
                transaction.category(),
                transaction.type(),
                transaction.createdAt()
        );
    }

    private Transaction toDomain(TransactionJpaEntity entity) {
        return new Transaction(
                entity.getId(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getCategory(),
                entity.getType(),
                entity.getCreatedAt()
        );
    }
}
