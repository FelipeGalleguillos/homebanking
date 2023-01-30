package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

//    @Autowired
//    LoanRepository loanRepository;
//    @Autowired
//    ClientRepository clientRepository;
//    @Autowired
//    TransactionRepository transactionRepository;
//
//    @Test
//    public void existLoans(){
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans,is(not(empty())));
//    }
//
//    @Test
//    public void existClients(){
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients,notNullValue());
//    }
//
//    @Test
//    public void existTransactions(){
//        String from = "2023-01-13 00:00:00.00000";
//        String to = "2023-01-19 23:59:59.00000";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS");
//        LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter);
//        LocalDateTime toDateTime = LocalDateTime.parse(to, formatter);
//        List<Transaction> transactions = transactionRepository.findAccountTransactionsBetweenDates(13L,fromDateTime,toDateTime);
//        assertThat(transactions,hasSize(1));
//    }



}
