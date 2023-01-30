package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository <Transaction, Long> {

//    @Query(value = "SELECT * FROM transaction WHERE account_id = ?1 AND date BETWEEN ?2 AND ?3", nativeQuery = true)
    @Query(value = "SELECT * FROM transaction t WHERE t.account_id=:accId AND t.date BETWEEN :fromDate AND :toDate", nativeQuery = true)
    public List<Transaction> findAccountTransactionsBetweenDates(@Param("accId") Long accId,@Param("fromDate") LocalDateTime fromDate,@Param("toDate") LocalDateTime toDate);
}
