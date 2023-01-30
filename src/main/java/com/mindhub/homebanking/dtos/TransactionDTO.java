package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private long id;
    private TransactionType type;
    private double ammount;
    private String description;
    private LocalDateTime date;

    public TransactionDTO(){}

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.ammount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
    }

    public long getId() {return id;}

    public TransactionType getType() {return type;}

    public double getAmmount() {return ammount;}

    public String getDescription() {return description;}

    public LocalDateTime getDate() {return date;}
}
