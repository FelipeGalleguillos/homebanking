package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private int maxAmount;
    private double percentage;
    @ElementCollection
    @Column(name="payment")
    private List<Integer> payments = new ArrayList<>();
    @OneToMany(mappedBy ="loan", fetch=EAGER)
    private Set<ClientLoan> loans = new HashSet<>();

    public Loan(){};

    public Loan(String name, int maxAmount, double percentage) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.percentage = percentage;
    }

    public long getId() {return id;}

    public String getName() {return name;}

    public int getMaxAmount() {return maxAmount;}

    public void setName(String name) {this.name = name;}

    public void setMaxAmount(int maxAmount) {this.maxAmount = maxAmount;}

    public double getPercentage() {return percentage;}

    public void setPercentage(double percentage) {this.percentage = percentage;}

    public List<Integer> getPayments() {return payments;}
    public void setPayments(List<Integer> payments) {this.payments = payments;}

    public void addpayment(Integer payment){payments.add(payment);}

    public Set<ClientLoan> getLoans() {return loans;}

    public void addLoan(ClientLoan loan) {
        loan.setLoan(this);
        loans.add(loan);
    }
}
