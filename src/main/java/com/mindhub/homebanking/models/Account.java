package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    private AccountType type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy ="account", fetch=EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Account(){}

    public Account(String number, LocalDate creationDate, double balance, AccountType accountType) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.type = accountType;
    }

    public long getId() {return id;}

    public String getNumber() {return number;}

    public void setNumber(String number) {this.number = number;}

    public LocalDate getCreationDate() {return creationDate;}

    public void setCreationDate(LocalDate creationDate) {this.creationDate = creationDate;}

    public double getBalance() {return balance;}

    public void setBalance(double balance) {this.balance = balance;}

    public AccountType getType() {return type;}

    public void setAccountType(AccountType accountType) {this.type = accountType;}

    @JsonIgnore
    public Client getClient() {return client;}

    public void setClient(Client client){this.client = client;}

    public Set<Transaction> getTransactions() {return transactions;}

    public void addTransaction(Transaction transaction) {

        if (transaction.getType() == TransactionType.CREDITO){
            this.setBalance(this.getBalance() + transaction.getAmount());
        }else{
            this.setBalance(this.getBalance() - transaction.getAmount());
        }
        transaction.setAccount(this);
        transaction.setAccountBalance(this.getBalance());
        transactions.add(transaction);
    }
}
