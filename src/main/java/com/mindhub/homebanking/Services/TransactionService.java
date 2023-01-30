package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<Transaction> getTransactionsBetweenDates(Long id,String fromDate, String toDate);

    void saveTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();

    Transaction getTransactionById(Long id);
}
