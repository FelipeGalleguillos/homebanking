package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {

    private Long id;
    private String name;
    private int maxAmount;
    private double percentage;
    private List<Integer> payments;

    public LoanDTO(){}

    public LoanDTO(Loan loan){
        this.id = loan.getId();
        this.maxAmount = loan.getMaxAmount();
        this.name = loan.getName();
        this.payments = loan.getPayments();
        this.percentage = loan.getPercentage();
    }

    public Long getId() {return id;}

    public String getName() {return name;}

    public int getMaxAmount() {return maxAmount;}

    public List<Integer> getPayments() {return payments;}

    public double getPercentage() {return percentage;}
}
