package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class AccountDTO {

    long id;
    String number;
    LocalDate creationDate;
    double balance;

    private Set<Transaction> transactions = new HashSet<>();

    public AccountDTO(){}

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions();
    }

    public long getId() {return id;}

    public String getNumber() {return number;}

    public LocalDate getCreationDate() {return creationDate;}

    public double getBalance() {return balance;}

    public Set<Transaction> getTransactions() {return transactions;}
}
