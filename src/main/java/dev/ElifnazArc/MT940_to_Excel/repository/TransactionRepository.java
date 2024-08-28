package dev.ElifnazArc.MT940_to_Excel.repository;

import dev.ElifnazArc.MT940_to_Excel.entity.Transaction;
import org.springframework.data.repository.ListCrudRepository;

public interface TransactionRepository extends ListCrudRepository<Transaction, Integer> {
}
