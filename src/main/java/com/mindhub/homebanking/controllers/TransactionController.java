package com.mindhub.homebanking.controllers;

import com.lowagie.text.DocumentException;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;


    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions() {
        return transactionService.getAllTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(toList());
    }

    @GetMapping("/transactions/export/pdf")
    public void exportToPDF(HttpServletResponse response, @RequestParam Long id, @RequestParam String fromDate, @RequestParam String toDate) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Transaction> listTransactions = transactionService.getTransactionsBetweenDates(id,fromDate,toDate);
        Account account = accountService.getAccountById(id);

        TransactionPDFExporter exporter = new TransactionPDFExporter(listTransactions,account);
        exporter.export(response);
    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getAccount(@PathVariable Long id) {
        return new TransactionDTO(transactionService.getTransactionById(id));
    }

    @Transactional
    @PostMapping(path = "/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication, @RequestParam String fromNumber,@RequestParam String toNumber,@RequestParam double amount,@RequestParam String description){

        Account originAccount = accountService.getAccountByNumber(fromNumber);
        Account destinyAccount = accountService.getAccountByNumber(toNumber);
        Client client = clientService.getClientByEmail(authentication.getName());

        if(fromNumber.isEmpty()){
            return new ResponseEntity<>("Origin account number missing",HttpStatus.FORBIDDEN);
        }
        if(toNumber.isEmpty()){
            return new ResponseEntity<>("Destiny account number missing",HttpStatus.FORBIDDEN);
        }
        if(amount <= 0){
            return new ResponseEntity<>("invalid amount",HttpStatus.FORBIDDEN);
        }
        if(description.isEmpty()){
            return new ResponseEntity<>("Description missing",HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(originAccount)){
            return new ResponseEntity<>("Account doesn't belong to current client",HttpStatus.FORBIDDEN);
        }
        if(originAccount == null){
            return new ResponseEntity<>("Origin account doesn't exist",HttpStatus.FORBIDDEN);
        }
        if(destinyAccount == null){
            return new ResponseEntity<>("Destiny account doesn't exist",HttpStatus.FORBIDDEN);
        }
        if(originAccount.getBalance() < amount){
            return new ResponseEntity<>("not enough funds",HttpStatus.FORBIDDEN);
        }
        if(fromNumber.equals(toNumber)){
            return new ResponseEntity<>("Origin account same as destiny account",HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction = new Transaction(TransactionType.DEBITO,amount,"Destiny acc:"+destinyAccount.getNumber()+" - "+description, LocalDateTime.now());
        Transaction creditTransaction = new Transaction(TransactionType.CREDITO,amount,"Origin acc:"+originAccount.getNumber()+" - "+description, LocalDateTime.now());

        originAccount.addTransaction(debitTransaction);
        debitTransaction.setAccount(originAccount);
        destinyAccount.addTransaction(creditTransaction);
        creditTransaction.setAccount(destinyAccount);

        accountService.saveAccount(originAccount);
        accountService.saveAccount(destinyAccount);
        transactionService.saveTransaction(debitTransaction);
        transactionService.saveTransaction(creditTransaction);

        return new ResponseEntity<>("Transaction succesfull",HttpStatus.CREATED);
    }

}

