package io.budgeting.application.port;

import io.budgeting.domain.Transaction;

import java.util.List;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    List<Transaction> findByCategory(String category);
}
