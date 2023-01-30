package com.mindhub.homebanking.Services.Impl;

import com.mindhub.homebanking.Services.LoanService;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;
    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
    @Override
    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId).orElse(null);
    }
}
