package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {

    private Long loanId;
    private double amount;
    private int payment;
    private String accNumber;

    public LoanApplicationDTO(){}

    public LoanApplicationDTO(Long loanId, double amount, int payment, String accNumber) {
        this.loanId = loanId;
        this.amount = amount;
        this.payment = payment;
        this.accNumber = accNumber;
    }

    public Long getLoanId() {return loanId;}

    public double getAmount() {return amount;}

    public int getPayment() {return payment;}

    public String getAccNumber() {return accNumber;}
}
