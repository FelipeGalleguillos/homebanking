package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.*;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanService loanService;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientLoanService clientLoanService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    @GetMapping(path="/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getAllLoans().stream().map(loan->new LoanDTO(loan)).collect(toList());
    }

    @Transactional
    @PostMapping(path="/loans")
    public ResponseEntity<Object> loanApply(Authentication authentication, @RequestBody LoanApplicationDTO loanAplication){

        Account account = accountService.getAccountByNumber(loanAplication.getAccNumber());
        Client client = clientService.getClientByEmail(authentication.getName());
        Loan loan = loanService.getLoanById(loanAplication.getLoanId());

        if(loan == null){
            return new ResponseEntity<>("Loan doesn't exist", HttpStatus.FORBIDDEN);
        }
        if(account == null){
            return new ResponseEntity<>("account doesn't exist",HttpStatus.FORBIDDEN);
        }
       if(client.getClientLoans().stream().map(clientLoan -> clientLoan.getLoan().getName()).collect(Collectors.toSet()).contains(loanService.getLoanById(loanAplication.getLoanId()).getName())){
           return new ResponseEntity<>("loan already applied",HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("Account doesn't belong to current user",HttpStatus.FORBIDDEN);
        }
        if(loan.getMaxAmount() < loanAplication.getAmount()){
            return new ResponseEntity<>("invalid amount",HttpStatus.FORBIDDEN);
        }
        if(!loan.getPayments().contains(loanAplication.getPayment())){
            return new ResponseEntity<>("invalid payment",HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanAplication.getAmount()+(loanAplication.getAmount()* loan.getPercentage()/100),loanAplication.getPayment(),LocalDate.now(),client,loan);
        Transaction transaction = new Transaction(TransactionType.CREDIT, loanAplication.getAmount(),loan.getName()+" - loan approved", LocalDateTime.now());

        client.addLoan(clientLoan);
        clientLoan.setClient(client);
        clientLoan.setLoan(loan);
        loan.addLoan(clientLoan);
        account.addTransaction(transaction);

        clientService.saveClient(client);
        accountService.saveAccount(account);
        clientLoanService.saveClientLoan(clientLoan);
        transactionService.saveTransaction(transaction);

        return new ResponseEntity<>("created",HttpStatus.CREATED);

    }
}
